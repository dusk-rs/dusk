package rs.dusk.game.entity.character

import rs.dusk.core.action.Action
import rs.dusk.engine.entity.character.update.LocalChange
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
	 * The index the character holds
	 */
	var index : Int = -1,
	
	/**
	 * The size of the character
	 */
	open var size : Size = Size.TILE,
	
	/**
	 * The action system instance
	 */
	open val action : Action,
	
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
	val containers : MutableMap<Int, Container> = mutableMapOf(),
	
	val effects : CharacterEffects,
	
	val values: CharacterValues,
	
	val variables : MutableMap<Int, Any> = mutableMapOf()

) : Entity() {
	
	fun remove(key : String) : Boolean? {
		TODO("Not yet implemented")
	}
	
	var walkDirection : Int = -1
	
	var runDirection : Int = -1
	
	var change : LocalChange? = null
	
	var changeValue : Int = -1
	
	/**
	 * The movement instance
	 */
	val movement : Movement = Movement(previousTile = tile)
	
}