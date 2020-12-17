package rs.dusk.game.model

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class Player(val username : String) : Character() {
	
	override var tile = Tile.DEFAULT
}