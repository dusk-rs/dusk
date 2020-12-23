package rs.dusk.game.entity.registry

import inject
import org.koin.dsl.module
import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier.INVERSE
import rs.dusk.core.network.packet.PacketType.SHORT
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.game.entity.character.player.Player
import rs.dusk.game.world.World
import rs.dusk.game.world.map.decrypt.XteaLoader
import rs.dusk.network.codec.game.encode.message.MapRegionMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 18, 2020
 */
class PlayerRegistry {
	
	private val xteaLoader : XteaLoader by inject()
	
	/*
	 * normal map region
	 */
	fun sendMapRegion(player: Player, sendLswp : Boolean) {
		val stream = PacketWriter().apply {
			writeOpcode(43, SHORT)
			
			if (sendLswp) {
				player.rendering.init(this)
			}
			
			writeByte(104, INVERSE)
			writeByte(0)
			writeShort(player.tile.getChunkX(), order = Endian.LITTLE)
			writeShort(player.tile.getChunkY())
			
			for (regionId in player.getRegionIds()) {
				val xteas : IntArray = xteaLoader.get(regionId)
				for (index in 0..3) {
					writeInt(xteas[index])
				}
			}
		}
		player.session.send(stream)
	}
	
	fun register(player : Player) {
		World.addGamePlayer(player)
		
//		sendMapRegion(player, true)
		
		val regionIds = player.getRegionIds()
		
		val xteaList = mutableListOf<IntArray>()
		
		for (regionId in regionIds) {
			val xteas = xteaLoader.get(regionId)
			xteaList.add(xteas)
		}
		
		// register player
		player.session.send(
			MapRegionMessage(
				chunkX = player.tile.getChunkX(),
				chunkY = player.tile.getChunkY(),
				forceReload = false,
				mapSize = 0,
				xteas = xteaList.toTypedArray(),
				clientIndex = 1,
				clientTile = player.tile.get30BitsLocationHash(),
				sendLswp = true,
				render = player.rendering
			)
		)
	}
}

val playerRegistryModule = module {
	single { PlayerRegistry() }
}
