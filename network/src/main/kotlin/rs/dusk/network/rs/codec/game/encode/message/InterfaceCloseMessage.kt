package rs.dusk.network.rs.codec.game.encode.message

import rs.dusk.core.network.model.message.Message

/**
 * This message is sent when we want to close an interface on a specific window.
 *
 * @author Tyluur <contact@kiaira.tech>
 * @since June 16, 2020
 */
data class InterfaceCloseMessage(

    /**
     * The interface id we wish to remove from the display
     */
    val interfaceId: Int,

    /**
     * The component id of the interface we wish to remove from the display
     */
    val interfaceComponentId: Int

) : Message