package rs.dusk.network.rs.codec.game.encode

import rs.dusk.core.io.Endian
import rs.dusk.core.network.codec.packet.access.PacketWriter
import rs.dusk.network.rs.codec.game.GameMessageEncoder
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_CLOSE
import rs.dusk.network.rs.codec.game.encode.message.InterfaceCloseMessage

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since June 16, 2020
 */
class InterfaceCloseMessageEncoder : GameMessageEncoder<InterfaceCloseMessage>() {

    override fun encode(builder: PacketWriter, msg: InterfaceCloseMessage) = with(msg) {
        builder.writeOpcode(INTERFACE_CLOSE)
        builder.writeInt(interfaceId shl 16 xor interfaceId, order = Endian.LITTLE)
    }

}