package rs.dusk.engine.data

import org.koin.dsl.module
import rs.dusk.cache.definition.decoder.ItemDecoder
import rs.dusk.engine.client.ui.InterfaceManager
import rs.dusk.engine.client.ui.InterfacesLookup
import rs.dusk.engine.client.ui.PlayerInterfaceIO
import rs.dusk.engine.client.ui.dialogue.Dialogues
import rs.dusk.engine.client.ui.dialogue.PlayerDialogueIO
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.event.EventBus
import rs.dusk.engine.map.Tile
import rs.dusk.utility.getProperty

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 03, 2020
 */
class PlayerLoader(private val bus: EventBus, private val interfaces: InterfacesLookup, private val decoder: ItemDecoder, strategy: StorageStrategy<Player>) : DataLoader<Player>(strategy) {

    private val x = getProperty("homeX", 0)
    private val y = getProperty("homeY", 0)
    private val plane = getProperty("homePlane", 0)
    private val tile = Tile(x, y, plane)


    fun loadPlayer(name: String): Player {
        val player = super.load(name) ?: Player(id = -1, tile = tile)
        val interfaceIO = PlayerInterfaceIO(player, bus)
        player.interfaces = InterfaceManager(interfaceIO, interfaces, player.gameFrame)
        val dialogueIO = PlayerDialogueIO(player, decoder)
        player.dialogues = Dialogues(dialogueIO, player)
        return player
    }
}

val playerLoaderModule = module {
    single { PlayerLoader(get(), get(), get(), get()) }
}