package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.InterfaceUpdateMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_WINDOW

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 18, 2020
 */
class InterfaceUpdateMessageEncoder : GameMessageEncoder<InterfaceUpdateMessage>() {
	
	override fun encode(builder : PacketWriter, msg : InterfaceUpdateMessage) {
		val (id, type) = msg
		builder.apply {
			writeOpcode(INTERFACE_WINDOW, PacketType.FIXED)
			writeShort(id, Modifier.ADD, Endian.LITTLE)
			writeByte(type, Modifier.SUBTRACT)
		}
	}
}