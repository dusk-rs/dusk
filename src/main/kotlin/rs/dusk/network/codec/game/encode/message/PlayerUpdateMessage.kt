package rs.dusk.network.codec.game.encode.message

import rs.dusk.core.network.buffer.write.BufferWriter
import rs.dusk.network.codec.game.GameServiceMessage

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @author Tyluur <itstyluur@gmail.com>
 *
 * @since April 18, 2020
 * @since December 25, 2020
 */
data class PlayerUpdateMessage(
	val changes : BufferWriter = BufferWriter(),
	val updates : BufferWriter = BufferWriter()
) :
	GameServiceMessage {
	
	fun release() {
		changes.buffer.clear()
		updates.buffer.clear()
	}
	
}