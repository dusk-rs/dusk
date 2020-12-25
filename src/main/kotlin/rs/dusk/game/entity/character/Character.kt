package rs.dusk.game.entity.character

import rs.dusk.core.action.Action
import rs.dusk.engine.entity.character.update.Visuals
import rs.dusk.game.container.Container
import rs.dusk.game.entity.Entity
import rs.dusk.game.entity.Size
import rs.dusk.game.entity.character.move.Movement
import rs.dusk.game.entity.character.player.data.Viewport

/**
 * An entity can be a player or an npc
 *
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
abstract class Character(
	
	/**
	 * The action system instance
	 */
	val action : Action = Action(),
	
	/**
	 * The index the character holds
	 */
	var index : Int = -1,
	
	/**
	 * The size of the character
	 */
	var size : Size = Size.TILE,
	
	/**
	 * The visual rendering instance
	 */
	val visuals : Visuals = Visuals(),
	
	/**
	 * The viewport instance
	 */
	val viewport : Viewport = Viewport(),
	
	/**
	 * The containers of the character
	 */
	val containers : MutableMap<Int, Container> = mutableMapOf()

) : Entity() {
	
	/**
	 * The movement instance
	 */
	val movement : Movement = Movement(previousTile = tile)
	
}