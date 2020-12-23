package rs.dusk.game.entity.registry

import inject
import org.koin.dsl.module
import rs.dusk.game.entity.character.player.Player
import rs.dusk.game.world.World
import rs.dusk.game.world.map.decrypt.XteaLoader
import rs.dusk.network.codec.game.encode.message.DrawWindowPaneMessage
import rs.dusk.network.codec.game.encode.message.MapRegionMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 18, 2020
 */
class PlayerRegistry {
	
	private val xteaLoader : XteaLoader by inject()
	
	fun register(player : Player) {
		World.addGamePlayer(player)
		
		val regionIds = player.getRegionIds()
		
		val xteaList = mutableListOf<IntArray>()
		
		// populate the xtea list with xteas for all local regions
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
		
		// send game window pane
		player.session.send(DrawWindowPaneMessage(548, 0))
	}
}

val playerRegistryModule = module {
	single { PlayerRegistry() }
}
