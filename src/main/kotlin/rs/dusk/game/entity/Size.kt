package rs.dusk.game.entity

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @author Tyluur <itstyluur@gmail.com>
 * @since May 18, 2020
 */
data class Size(val width : Int, val height : Int) {
	companion object {
		val TILE = Size(1, 1)
	}
}