package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.InterfaceSettingsMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_COMPONENT_SETTINGS

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since July 26, 2020
 */
class InterfaceSettingsMessageEncoder : GameMessageEncoder<InterfaceSettingsMessage>() {

    override fun encode(builder: PacketWriter, msg: InterfaceSettingsMessage) {
        val (id, component, fromSlot, toSlot, settings) = msg
        builder.apply {
            writeOpcode(INTERFACE_COMPONENT_SETTINGS)
            writeShort(fromSlot, order = Endian.LITTLE)
            writeInt(id shl 16 or component, Modifier.INVERSE, Endian.MIDDLE)
            writeShort(toSlot, Modifier.ADD)
            writeInt(settings, order = Endian.LITTLE)
        }
    }
}