package rs.dusk.network.rs.codec.game.encode

import rs.dusk.core.io.Endian.LITTLE
import rs.dusk.core.io.Modifier.ADD
import rs.dusk.core.network.codec.packet.access.PacketWriter
import rs.dusk.core.network.model.packet.PacketType.FIXED
import rs.dusk.network.rs.codec.game.GameMessageEncoder
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_OPEN
import rs.dusk.network.rs.codec.game.encode.message.InterfaceOpenMessage

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since June 25, 2020
 */
class InterfaceOpenMessageEncoder : GameMessageEncoder<InterfaceOpenMessage>() {

    override fun encode(builder: PacketWriter, msg: InterfaceOpenMessage) = with(msg) {
        builder.apply {
            writeOpcode(INTERFACE_OPEN, FIXED)
            writeShort(interfaceId, type = ADD, order = LITTLE)
            writeInt((parentId shl 16 or index), order = LITTLE)
            writeByte(if (walkable) 1 else 0)
        }

    }

}