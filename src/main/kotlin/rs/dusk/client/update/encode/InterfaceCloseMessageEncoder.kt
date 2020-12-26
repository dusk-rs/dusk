package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.InterfaceCloseMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_CLOSE

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since July 25, 2020
 */
class InterfaceCloseMessageEncoder : GameMessageEncoder<InterfaceCloseMessage>() {

    override fun encode(builder: PacketWriter, msg: InterfaceCloseMessage) {
        val (id, component) = msg
        builder.apply {
            writeOpcode(INTERFACE_CLOSE)
            writeInt(id shl 16 or component, order = Endian.LITTLE)
        }
    }
}