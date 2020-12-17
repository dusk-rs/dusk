package rs.dusk.game.model

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
data class Player(val username : String) : Character() {
	
	override var tile = Tile.DEFAULT
}