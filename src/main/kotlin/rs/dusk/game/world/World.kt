package rs.dusk.game.world

import org.koin.dsl.module
import rs.dusk.game.entity.character.CharacterList
import rs.dusk.game.entity.character.player.Player

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class World {
	
	/**
	 * The collection of players in the game.
	 */
	val gamePlayers = CharacterList<Player>(2048)
	
	/**
	 * The collection of players in the lobby
	 */
	val lobbyPlayers = CharacterList<Player>(2048)
	
	/**
	 * This function adds a player to the world.
	 */
	fun addGamePlayer(player : Player) : Boolean {
		return gamePlayers.add(player)
	}
	
	/**
	 * This player removes a player from the world.
	 */
	fun removeGamePlayer(player : Player) : Boolean {
		return gamePlayers.remove(player.index) != null
	}
	
	fun findPlayerByIndex(index : Int) : Player? {
		return gamePlayers.find { player -> player.index == index }
	}
	
}

val worldModule = module { single(createdAtStart = true) { World() } }