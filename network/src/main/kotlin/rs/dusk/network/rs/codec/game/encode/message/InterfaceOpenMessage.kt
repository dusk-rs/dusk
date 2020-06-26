package rs.dusk.network.rs.codec.game.encode.message

import rs.dusk.core.network.model.message.Message

/**
 * @author Tyluur <contact@kiaira.tech>
 *
 * @param interfaceId The id of the interface to open
 * @param parentId The id of the parent interface which this interface will be drawn to
 * @param index The index on the [parent interface][parentId] to draw the [interface][interfaceId]
 * @param walkable If this interface can be clicked through
 *
 * @since June 09, 2020
 */
data class InterfaceOpenMessage(
    val parentId: Int, val index: Int, val interfaceId: Int, val walkable: Boolean
) : Message