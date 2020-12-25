package rs.dusk.network.rs.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since June 19, 2020
 * @param offset The tile offset from the [ChunkUpdateMessage] sent
 * @param id Item id
 */
data class FloorItemRemoveMessage(
	val offset : Int,
	val id : Int
) : GameMessage