package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.InterfaceHeadPlayerMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_PLAYER_HEAD
/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since August 2, 2020
 */
class InterfaceHeadPlayerMessageEncoder : GameMessageEncoder<InterfaceHeadPlayerMessage>() {

    override fun encode(builder: PacketWriter, msg: InterfaceHeadPlayerMessage) {
        val (id, component) = msg
        builder.apply {
            writeOpcode(INTERFACE_PLAYER_HEAD)
            writeInt(id shl 16 or component, order = Endian.LITTLE)
        }
    }
}