package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.buffer.Modifier.SUBTRACT
import rs.dusk.core.network.packet.PacketType.FIXED
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.DrawWindowPaneMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_WINDOW

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 23, 2020
 */
class DrawWindowPaneMessageEncoder : GameMessageEncoder<DrawWindowPaneMessage>() {
	
	override fun encode(builder : PacketWriter, msg : DrawWindowPaneMessage) = with(builder) {
		with(msg) {
			writeOpcode(INTERFACE_WINDOW, FIXED)
			writeShort(value = id, order = Endian.LITTLE, type = Modifier.ADD)
			writeByte(value = type, type = SUBTRACT)
		}
	}
	
}