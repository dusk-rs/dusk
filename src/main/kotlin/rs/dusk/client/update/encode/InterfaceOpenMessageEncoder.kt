package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.InterfaceOpenMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_OPEN

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since July 25, 2020
 */
class InterfaceOpenMessageEncoder : GameMessageEncoder<InterfaceOpenMessage>() {

    override fun encode(builder: PacketWriter, msg: InterfaceOpenMessage) {
        val (permanent, parent, component, id) = msg
        builder.apply {
            writeOpcode(INTERFACE_OPEN)
            writeShort(id, Modifier.ADD, Endian.LITTLE)
            writeInt(parent shl 16 or component, order = Endian.LITTLE)
            writeByte(permanent)
        }
    }
}