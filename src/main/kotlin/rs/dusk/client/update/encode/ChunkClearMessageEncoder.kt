package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.ChunkClearMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.CHUNK_CLEAR

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since June 21, 2020
 */
class ChunkClearMessageEncoder : GameMessageEncoder<ChunkClearMessage>() {

    override fun encode(builder: PacketWriter, msg: ChunkClearMessage) {
        val (x, y, plane) = msg
        builder.apply {
            writeOpcode(CHUNK_CLEAR)
            writeByte(x)
            writeByte(y, Modifier.SUBTRACT)
            writeByte(plane)
        }
    }
}