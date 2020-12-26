package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.InterfaceHeadNPCMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_NPC_HEAD

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since August 2, 2020
 */
class InterfaceHeadNPCMessageEncoder : GameMessageEncoder<InterfaceHeadNPCMessage>() {

    override fun encode(builder: PacketWriter, msg: InterfaceHeadNPCMessage) {
        val (id, component, npc) = msg
        builder.apply {
            writeOpcode(INTERFACE_NPC_HEAD)
            writeInt(id shl 16 or component)
            writeShort(npc, order = Endian.LITTLE)
        }
    }
}