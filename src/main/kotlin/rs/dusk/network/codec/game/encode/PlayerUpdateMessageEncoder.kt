package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.PlayerUpdateMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.PLAYER_UPDATING

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @author Tyluur <itstyluur@gmail.com>
 * @since April 18, 2020
 * @since December 25, 2020
 */

class PlayerUpdateMessageEncoder : GameMessageEncoder<PlayerUpdateMessage>() {
	
	override fun encode(builder: PacketWriter, msg: PlayerUpdateMessage) {
		val (changes, updates) = msg
		builder.apply {
			writeOpcode(PLAYER_UPDATING, PacketType.SHORT)
			writeBytes(changes.buffer)
			writeBytes(updates.buffer)
		}
		msg.release()
	}
}