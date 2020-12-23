package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.game.world.World
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
		with(msg) {
			if (sendLswp) {
				startBitAccess()
				writeBits(30, msg.clientTile)
				render.localPlayers[render.player.index] = render.player
				render.localPlayersIndexes[render.localPlayersIndexesCount++] = render.player.index
				for (playerIndex in 1..2047) {
					if (playerIndex == render.player.index) {
						continue
					}
					val player = World.findPlayerByIndex(playerIndex)
					writeBits(
						18,
						player?.tile?.get18BitsLocationHash()?.also({ render.regionHashes[playerIndex] = it }) ?: 0
					)
					render.outPlayersIndexes[render.outPlayersIndexesCount++] = playerIndex
				}
				finishBitAccess()
			}
		}
		
		writeByte(msg.mapSize, Modifier.INVERSE)
		writeByte(msg.forceReload)
		writeShort(msg.chunkX, order = Endian.LITTLE)
		writeShort(msg.chunkY)
		
		msg.xteas.forEach {
			it.forEach { key ->
				writeInt(key)
			}
		}
	}
}