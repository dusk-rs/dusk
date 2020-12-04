package rs.dusk.game.model.player

import com.github.michaelbull.logging.InlineLogger
import rs.dusk.engine.client.send
import rs.dusk.engine.client.ui.InterfaceManager
import rs.dusk.engine.client.ui.InterfaceOptions
import rs.dusk.engine.client.ui.PlayerInterfaceIO
import rs.dusk.engine.client.ui.detail.InterfaceDetails
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.entity.definition.ContainerDefinitions
import rs.dusk.engine.event.EventBus
import rs.dusk.engine.io.strategy.YAMLStrategy
import rs.dusk.engine.map.Tile
import rs.dusk.engine.map.collision.Collisions
import rs.dusk.engine.path.strat.FollowTargetStrategy
import rs.dusk.engine.path.strat.RectangleTargetStrategy
import rs.dusk.network.rs.codec.game.encode.message.SkillLevelMessage
import rs.dusk.utility.get
import rs.dusk.utility.getProperty

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @author Greg Hibberd <greg@greghibberd.com>
 *
 * @since April 03, 2020
 */
class PlayerIO : YAMLStrategy(contents = "players") {

    private val logger = InlineLogger()

    private val x = getProperty("homeX", 0)
    private val y = getProperty("homeY", 0)
    private val plane = getProperty("homePlane", 0)
    private val tile = Tile(x, y, plane)

    private val bus: EventBus = get()
    private val collisions: Collisions = get()
    private val definitions: ContainerDefinitions = get()
    private val interfaces: InterfaceDetails = get()

    /**
     * Loads a player's file
     */
    fun loadPlayer(name: String, production: Boolean): Player {
        val loaded = super.load(name)
        if (loaded == null) {
            logger.trace { "New player constructed" }
        }
        val player = loaded ?: Player(id = -1, tile = tile)
        val interfaceIO = PlayerInterfaceIO(player, bus)
        player.interfaces = InterfaceManager(interfaceIO, interfaces, player.gameFrame)
        player.interfaceOptions = InterfaceOptions(player, interfaces, definitions)
        player.experience.addListener { skill, _, experience ->
            val level = player.levels.get(skill)
            player.send(SkillLevelMessage(skill.ordinal, level, experience.toInt()))
        }
        player.interactTarget = RectangleTargetStrategy(collisions, player)
        player.followTarget = FollowTargetStrategy(player)
        return player
    }

}

