package rs.dusk.game.entity.character

import rs.dusk.game.entity.Entity
import rs.dusk.game.entity.Size

/**
 * An entity can be a player or an npc
 *
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
abstract class Character : Entity() {
	
	/**
	 * The index the character holds
	 */
	var index : Int = -1
	
	/**
	 * The size of the character
	 */
	abstract var size : Size
}