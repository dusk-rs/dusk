package rs.dusk.game.entity

import rs.dusk.core.map.Tile

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
	
}