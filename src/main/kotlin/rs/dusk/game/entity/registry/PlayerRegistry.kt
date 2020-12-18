package rs.dusk.game.entity.registry

import org.koin.dsl.module
import rs.dusk.game.entity.character.player.Player
import rs.dusk.network.codec.game.encode.message.MapRegionMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 18, 2020
 */
class PlayerRegistry {
	
	fun register(player : Player) {
	
	}
}

val playerRegistryModule = module {
	single { PlayerRegistry() }
}