package rs.dusk.engine.model.entity.index.player.logic.interfaces

import com.github.michaelbull.logging.InlineLogger
import org.koin.dsl.module
import rs.dusk.engine.client.session.send
import rs.dusk.engine.model.entity.index.player.Player
import rs.dusk.network.rs.codec.game.encode.message.InterfaceOpenMessage
import rs.dusk.network.rs.codec.game.encode.message.WindowUpdateMessage
import rs.dusk.utility.constant.player.GameFrameComponent
import rs.dusk.utility.constant.player.InterfaceConstants.ChatBackground
import rs.dusk.utility.constant.player.InterfaceConstants.ChatBox
import rs.dusk.utility.constant.player.InterfaceConstants.FIXED_WINDOW_ID
import rs.dusk.utility.constant.player.InterfaceConstants.RESIZABLE_WINDOW_ID

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since June 25, 2020
 */
class InterfaceSystem {

    private val logger = InlineLogger()

    fun login(player: Player) {
        sendGameFrame(player)
    }

    fun sendGameFrame(player: Player) = with(player.interfaces) {
        val resizable = player.clientSettings.isResizable()
        val interfaceId = if (resizable) RESIZABLE_WINDOW_ID else FIXED_WINDOW_ID

        sendWindow(player, interfaceId, 0)
        sendInterface(player, ChatBox, ChatBackground, 9)

        GameFrameComponent.values().forEach { component ->
            val index = if (resizable) component.resizableIndex else component.fixedIndex
            if (component.interfaceIds.isNotEmpty()) {
                val childInterfaceId = component.interfaceIds[0]
                sendInterface(player, interfaceId = childInterfaceId, index = index)
                logger.info { "Sent $component" }
            } else {
                logger.info { "Skipped $component" }
            }
        }

    }

    fun sendWindow(player: Player, interfaceId: Int, index: Int) = with(player.interfaces) {
        if (addTopInterface(interfaceId, index)) {
            player.send(WindowUpdateMessage(interfaceId, index))
        } else {
            logger.warn { "Unable to add a window to the player's interface tree" }
        }
    }

    fun sendInterface(
        player: Player,
        parentId: Int? = null,
        interfaceId: Int,
        index: Int,
        walkable: Boolean = true
    ) =
        with(player.interfaces) {
            val window = getWindow() ?: throw IllegalStateException("Unable to find the window interface!")
            val windowId = parentId ?: window.interfaceId

            if (addInterface(window.interfaceId, interfaceId, index)) {
                player.send(InterfaceOpenMessage(windowId, index, interfaceId, walkable))
            } else {
                logger.warn { "Unable to add a tab to the player's interface tree" }
            }

        }

}

val interfaceSystemModule = module { single(createdAtStart = true) { InterfaceSystem() } }