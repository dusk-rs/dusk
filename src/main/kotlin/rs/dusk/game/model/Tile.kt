package rs.dusk.game.model

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
data class Tile(val x : Int, val y : Int, val plane : Int) {
	
	fun getChunkX() : Int {
		return x shr 3
	}
	
	fun getChunkY() : Int {
		return y shr 3
	}
	
	companion object {
		
		val DEFAULT = Tile(3333, 3333, 0)
	}
}