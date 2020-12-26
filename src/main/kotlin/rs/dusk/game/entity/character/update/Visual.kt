package rs.dusk.game.entity.character.update

import rs.dusk.game.entity.character.player.Player

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
interface Visual {
	/**
	 * Optional reset to be performed at the end of an update
	 */
	fun reset(character : Character) {
		TODO("Not yet implemented")
	}
	
	fun reset(player : Player) {
		TODO("Not yet implemented")
	}
}