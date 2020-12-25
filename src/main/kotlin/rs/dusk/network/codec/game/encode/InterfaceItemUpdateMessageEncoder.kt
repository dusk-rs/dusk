package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.ContainerItemUpdateMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_ITEMS_UPDATE

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since July 31, 2020
 */
class InterfaceItemUpdateMessageEncoder : GameMessageEncoder<ContainerItemUpdateMessage>() {
	
	override fun encode(builder : PacketWriter, msg : ContainerItemUpdateMessage) {
		val (key, updates, primary) = msg
		builder.apply {
			writeOpcode(INTERFACE_ITEMS_UPDATE, PacketType.SHORT)
			writeShort(key)
			writeByte(primary)
			for ((index, item, amount) in updates) {
				writeSmart(index)
				writeShort(item + 1)
				if (item >= 0) {
					writeByte(if (amount >= 255) 255 else amount)
					if (amount >= 255) {
						writeInt(amount)
					}
				}
			}
		}
	}
}