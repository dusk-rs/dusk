package rs.dusk.network.rs.codec.game.encode.message

import rs.dusk.core.network.buffer.write.BufferWriter
import rs.dusk.network.codec.game.GameMessage

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 18, 2020
 */
data class PlayerUpdateMessage(
	val changes : BufferWriter = BufferWriter(),
	val updates : BufferWriter = BufferWriter()
) :
	GameMessage {
	
	fun release() {
		changes.buffer.clear()
		updates.buffer.clear()
	}
}