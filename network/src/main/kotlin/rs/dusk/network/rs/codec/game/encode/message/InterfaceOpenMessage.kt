package rs.dusk.network.rs.codec.game.encode.message

import rs.dusk.core.network.model.message.Message

/**
 * @author Tyluur <contact@kiaira.tech>
 * @param interfaceId The id of the interface to open
 * @param windowId The id of the window in which the interface will be opened
 * @param windowComponentId The component id on the [window][windowId] to draw the [interface][interfaceId]
 * @param walkable If this interface can be clicked through
 *
 * @since June 09, 2020
 */
class InterfaceOpenMessage(

    val windowId: Int,

    val windowComponentId: Int,

    val interfaceId: Int,

    val walkable: Boolean

) : Message