package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since July 2, 2020
 * @param offset The tile offset from the [ChunkUpdateMessage] sent
 * @param id graphic id
 * @param height 0..255 start height off the ground
 * @param delay delay to start graphic 30 = 1 tick
 * @param rotation 0..7
 */
data class GraphicAreaMessage(
	val offset : Int,
	val id : Int,
	val height : Int,
	val delay : Int,
	val rotation : Int
) : GameMessage