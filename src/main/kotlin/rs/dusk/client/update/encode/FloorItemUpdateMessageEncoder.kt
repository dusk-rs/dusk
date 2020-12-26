package rs.dusk.client.update.encode

import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.FloorItemUpdateMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.FLOOR_ITEM_UPDATE

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since June 19, 2020
 */
class FloorItemUpdateMessageEncoder : GameMessageEncoder<FloorItemUpdateMessage>() {

    override fun encode(builder: PacketWriter, msg: FloorItemUpdateMessage) {
        val (tile, id, oldAmount, newAmount) = msg
        builder.apply {
            writeOpcode(FLOOR_ITEM_UPDATE)
            writeByte(tile)
            writeShort(id)
            writeShort(oldAmount)
            writeShort(newAmount)
        }
    }
}