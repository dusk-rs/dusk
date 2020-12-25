package rs.dusk.game.entity.character.player

import kotlinx.coroutines.CoroutineStart.DEFAULT
import rs.dusk.core.map.Tile
import rs.dusk.core.network.session.Session
import rs.dusk.engine.entity.character.move.Movement
import rs.dusk.engine.entity.character.update.Visuals
import rs.dusk.game.entity.Size
import rs.dusk.game.entity.character.Character
import rs.dusk.game.entity.character.player.data.Viewport

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
data class Player(val username : String, var session : Session) : Character() {
	
	override var tile = DEFAULT
	
	override var size : Size = Size.TILE
	val viewport : Viewport = Viewport()
	val visuals : Visuals = Visuals()
	val movement : Movement = Movement(tile)
	val action : Action = Action()
	
	var removed = false
	
	
}