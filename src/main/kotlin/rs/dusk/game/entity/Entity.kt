package rs.dusk.game.entity

import rs.dusk.game.world.map.Tile
import rs.dusk.game.world.map.WorldMap

/**
 * An entity is anything that is movable.
 *
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
abstract class Entity {
	
	/**
	 * The tile the entity is on
	 */
	abstract var tile : Tile
	
	fun getRegionIds() : List<Int> {
		val mapSize = 0
		val regionX : Int = tile.getChunkX()
		val regionY : Int = tile.getChunkY()
		val mapHash : Int = WorldMap.SIZES[mapSize] shr 4
		
		val mapRegionsIds = arrayListOf<Int>()
		
		for (xCalc in (regionX - mapHash) / 8..(regionX + mapHash) / 8) {
			for (yCalc in (regionY - mapHash) / 8..(regionY + mapHash) / 8) {
				val regionId : Int = yCalc + (xCalc shl 8)
				
				mapRegionsIds.add(yCalc + (xCalc shl 8))
			}
		}
		return mapRegionsIds
	}
}