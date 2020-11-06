package rs.dusk.engine.data

import com.github.michaelbull.logging.InlineLogger
import org.koin.dsl.module
import rs.dusk.engine.client.send
import rs.dusk.engine.client.ui.InterfaceManager
import rs.dusk.engine.client.ui.InterfaceOptions
import rs.dusk.engine.client.ui.PlayerInterfaceIO
import rs.dusk.engine.client.ui.detail.InterfaceDetails
import rs.dusk.engine.entity.character.contain.detail.ContainerDetails
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.entity.character.player.PlayerDetails
import rs.dusk.engine.entity.character.player.social.*
import rs.dusk.engine.event.EventBus
import rs.dusk.engine.map.Tile
import rs.dusk.engine.map.collision.Collisions
import rs.dusk.engine.path.strat.FollowTargetStrategy
import rs.dusk.engine.path.strat.RectangleTargetStrategy
import rs.dusk.network.rs.codec.game.encode.message.SkillLevelMessage
import rs.dusk.utility.getProperty
import rs.dusk.utility.inject

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 03, 2020
 */
class PlayerLoader(private val bus: EventBus, private val interfaces: InterfaceDetails, private val collisions: Collisions, private val containers: ContainerDetails, strategy: StorageStrategy<Player>) : DataLoader<Player>(strategy) {

    private val x = getProperty("homeX", 0)
    private val y = getProperty("homeY", 0)
    private val plane = getProperty("homePlane", 0)
    private val tile = Tile(x, y, plane)

    private val logger = InlineLogger()

    private val namesList: NamesList by inject()

    private val relations: RelationshipManager by inject()

    private val channels: FriendsChatChannels by inject()

    fun loadPlayer(name: String): Player {
        val playerNames = namesList.getUserName(name) ?: let {
            val names = Names(name)
            namesList.addName(names)
            logger.info { "Creating new player: $name" }
            names
        }
        //todo: rights
        val player = super.load(name)
            ?: Player(id = -1,
                tile = tile,
                names = playerNames,
                details = PlayerDetails(name = playerNames, rights = 2),
                relations = relations.get(playerNames) ?: relations.create(playerNames)
            )
        val interfaceIO = PlayerInterfaceIO(player, bus)
        player.interfaces = InterfaceManager(interfaceIO, interfaces, player.gameFrame)
        player.interfaceOptions = InterfaceOptions(player, interfaces, containers)
        player.experience.addListener { skill, _, experience ->
            val level = player.levels.get(skill)
            player.send(SkillLevelMessage(skill.ordinal, level, experience.toInt()))
        }
        player.interactTarget = RectangleTargetStrategy(collisions, player)
        player.followTarget = FollowTargetStrategy(player)
        channels.name(player, player.details.name.name)
        return player
    }
}

val playerLoaderModule = module {
    single { PlayerLoader(get(), get(), get(), get(), get()) }
}