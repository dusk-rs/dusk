package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.InterfaceVisibilityMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_COMPONENT_VISIBILITY

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since August 2, 2020
 */
class InterfaceVisibilityMessageEncoder : GameMessageEncoder<InterfaceVisibilityMessage>() {
	
	override fun encode(builder : PacketWriter, msg : InterfaceVisibilityMessage) {
		val (id, component, visible) = msg
		builder.apply {
			writeOpcode(INTERFACE_COMPONENT_VISIBILITY)
			writeInt(id shl 16 or component, order = Endian.MIDDLE)
			writeByte(visible, Modifier.ADD)
		}
	}
}