package rs.dusk.game.entity.character

import rs.dusk.game.entity.Entity

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
abstract class Character : Entity() {
	
	/**
	 * The index the character holds
	 */
	var index : Int = -1
}