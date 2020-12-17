package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameServiceMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17th, 2020
 */
data class MapRegionMessage(
	val mapSize : Int,
	val force : Boolean,
	val chunkX : Int,
	val chunkY : Int,
	val xteas : IntArray
) : GameServiceMessage {
}
