package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.ContainerItemsMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_ITEMS

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since July 31, 2020
 */
class ContainerItemsMessageEncoder : GameMessageEncoder<ContainerItemsMessage>() {

    override fun encode(builder: PacketWriter, msg: ContainerItemsMessage) {
        val (key, items, amounts, primary) = msg
        builder.apply {
            writeOpcode(INTERFACE_ITEMS, PacketType.SHORT)
            writeShort(key)
            writeByte(primary)
            writeShort(items.size)
            for((index, item) in items.withIndex()) {
                val amount = amounts[index]
                writeByte(if (amount >= 255) 255 else amount)
                if (amount >= 255) {
                    writeInt(amount)
                }
                writeShort(item + 1, order = Endian.LITTLE)
            }
        }
    }
}