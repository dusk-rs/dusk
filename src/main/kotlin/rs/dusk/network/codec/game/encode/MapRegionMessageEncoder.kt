package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.MapRegionMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.REGION

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 18, 2020
 */
class MapRegionMessageEncoder : GameMessageEncoder<MapRegionMessage>() {
	
	override fun encode(builder : PacketWriter, msg : MapRegionMessage) {
		val (chunkX, chunkY, forceRefresh, mapSize, xteas, clientIndex, clientTile, playerRegions) = msg
		builder.apply {
			writeOpcode(REGION, PacketType.SHORT)
			if (playerRegions != null && clientTile != null && clientIndex != null) {
				startBitAccess()
				writeBits(30, clientTile)
				playerRegions.forEachIndexed { index, region ->
					if (index != clientIndex) {
						writeBits(18, region)
					}
				}
				finishBitAccess()
			}
			writeByte(mapSize, Modifier.INVERSE)
			writeByte(forceRefresh)
			writeShort(chunkX, order = Endian.LITTLE)
			writeShort(chunkY)
			xteas.forEach {
				it.forEach { key ->
					writeInt(key)
				}
			}
		}
	}
}