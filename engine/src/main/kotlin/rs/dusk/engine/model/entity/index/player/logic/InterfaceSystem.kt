package rs.dusk.engine.model.entity.index.player.logic

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

    /**
     * Send the gameframe to the player's client as well as store all components in the player's collection
     */
    fun sendGameFrame(player: Player) = with(player.interfaces) {
        val resizable = player.clientSettings.isResizable()
        val windowInterfaceId = if (resizable) RESIZABLE_WINDOW_ID else FIXED_WINDOW_ID

        // the main window must be sent separately
        sendWindow(player, windowInterfaceId, 0)

        // send the chatbox background separately
        sendInterface(player, parentId = ChatBox, interfaceId = ChatBackground, index = 9)

        // send all gameframe components
        with(GameFrameComponent.values()) {
           forEach { component ->
                val index = if (resizable) component.resizableIndex else component.fixedIndex
                val childInterfaceId = component.interfaceIds[0]
                sendInterface(player, interfaceId = childInterfaceId, index = index)
            }
        }
    }

    /**
     * Send a window to the players client as well as stores it in their collection
     */
    fun sendWindow(player: Player, interfaceId: Int, index: Int) = with(player.interfaces) {
        storeWindow(interfaceId)
        player.send(WindowUpdateMessage(interfaceId, index))
    }

    /**
     * Sends an interface to the player's client as well as stores it in their collection
     * @param parentId The parent interface id to draw and store the interface to. This allows for interface storage in a tree data structure.
     * @param interfaceId The id of the interface to display & store
     * @param index The component index of the parent interface on which the interface will be drawn onto
     */
    fun sendInterface(player: Player, parentId: Int? = null, interfaceId: Int, index: Int, walkable: Boolean = true) =
        with(player.interfaces) {
            // an interface can either be sent as a child of the topmost window or as a child of an existent interface [parent]

            val windowInterfaceId = getWindowInterfaceId()
            val windowId = parentId ?: windowInterfaceId

            storeInterface(windowInterfaceId, interfaceId)
            player.send(InterfaceOpenMessage(windowId, index, interfaceId, walkable))
        }

}

val interfaceSystemModule = module { single(createdAtStart = true) { InterfaceSystem() } }