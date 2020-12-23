package rs.dusk.game.entity.character.player

import rs.dusk.core.network.session.Session
import rs.dusk.game.entity.character.Character
import rs.dusk.game.entity.character.player.render.PlayerRendering
import rs.dusk.game.world.map.Tile

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
data class Player(val username : String, var session : Session) : Character() {
	
	override var tile = Tile.DEFAULT
	
	val rendering = PlayerRendering(this)
	
	var removed = false
	
	
}