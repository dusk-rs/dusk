package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.MapRegionMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.REGION

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 18, 2020
 */
class MapRegionMessageEncoder : GameMessageEncoder<MapRegionMessage>() {
	
	override fun encode(builder : PacketWriter, msg : MapRegionMessage) = with(builder) {
		
		writeOpcode(REGION, PacketType.SHORT)
		startBitAccess()
		writeBits(30, msg.clientTile)
		for (i in 0..2048) {
			if (i != 1) {
				writeBits(18, 0)
			}
		}
		finishBitAccess()
		writeByte(msg.mapSize, Modifier.INVERSE)
		writeByte(msg.forceReload)
		writeShort(msg.chunkX, order = Endian.LITTLE)
		writeShort(msg.chunkY)
		msg.xteas.forEach {
			writeInt(it)
		}
	}
}