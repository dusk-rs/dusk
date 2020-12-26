package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.ChunkUpdateMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.UPDATE_CHUNK

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since June 19, 2020
 */
class ChunkUpdateMessageEncoder : GameMessageEncoder<ChunkUpdateMessage>() {
	
	override fun encode(builder : PacketWriter, msg : ChunkUpdateMessage) {
		val (x, y, plane) = msg
		builder.apply {
			writeOpcode(UPDATE_CHUNK)
			writeByte(x, Modifier.ADD)
			writeByte(y)
			writeByte(plane, type = Modifier.SUBTRACT)
		}
	}
}