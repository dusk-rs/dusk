package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.ContextMenuOptionMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.PLAYER_OPTION

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since August 16, 2020
 */
class ContextMenuOptionMessageEncoder : GameMessageEncoder<ContextMenuOptionMessage>() {
	
	override fun encode(builder : PacketWriter, msg : ContextMenuOptionMessage) {
		val (option, slot, top, cursor) = msg
		builder.apply {
			writeOpcode(PLAYER_OPTION, PacketType.BYTE)
			writeByte(top, Modifier.ADD)
			writeShort(cursor, order = Endian.LITTLE)
			writeString(option)
			writeByte(slot, Modifier.INVERSE)
		}
	}
}