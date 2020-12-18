package rs.dusk.game.entity.character.player

import rs.dusk.game.entity.character.Character
import rs.dusk.game.world.map.Tile

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
data class Player(val username : String) : Character() {
	
	override var tile = Tile.DEFAULT
}