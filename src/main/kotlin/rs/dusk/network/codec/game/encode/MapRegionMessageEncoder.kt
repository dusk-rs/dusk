package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.game.model.Tile
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.MapRegionMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.REGION

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 18, 2020
 */
class MapRegionMessageEncoder : GameMessageEncoder<MapRegionMessage>() {
	
	override fun encode(builder : PacketWriter, msg : MapRegionMessage) {
		val (mapSize, force, chunkX, chunkY, xteas) = msg
		builder.apply {
			writeOpcode(REGION, PacketType.SHORT)
			
			startBitAccess()
			writeBits(30, Tile.DEFAULT.get30BitsLocationHash())
			finishBitAccess()
			
			writeByte(mapSize, Modifier.INVERSE)
			writeByte(force)
			writeShort(chunkX, order = Endian.LITTLE)
			writeShort(chunkY)
			xteas.forEach {
				writeInt(it)
			}
		}
	}
}