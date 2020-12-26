package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.GraphicAreaMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.GRAPHIC_AREA

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since June 27, 2020
 */
class GraphicAreaMessageEncoder : GameMessageEncoder<GraphicAreaMessage>() {
	
	override fun encode(builder : PacketWriter, msg : GraphicAreaMessage) {
		val (tile, id, height, delay, rotation) = msg
		builder.apply {
			writeOpcode(GRAPHIC_AREA)
			writeByte(tile, type = Modifier.ADD)
			writeShort(id)
			writeByte(height)
			writeShort(delay)
			writeByte(rotation)//0..7
		}
	}
}