package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.FloorItemRevealMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.FLOOR_ITEM_REVEAL

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since June 19, 2020
 */
class FloorItemRevealMessageEncoder : GameMessageEncoder<FloorItemRevealMessage>() {
	
	override fun encode(builder : PacketWriter, msg : FloorItemRevealMessage) {
		val (tile, id, amount, owner) = msg
		builder.apply {
			writeOpcode(FLOOR_ITEM_REVEAL)
			writeShort(owner, type = Modifier.ADD)
			writeByte(tile, type = Modifier.ADD)
			writeShort(id, order = Endian.LITTLE)
			writeShort(amount)
		}
	}
}