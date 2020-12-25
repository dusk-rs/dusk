package rs.dusk.game.entity.character.player

import rs.dusk.core.map.Tile
import rs.dusk.core.network.session.Session
import rs.dusk.game.entity.Size
import rs.dusk.game.entity.character.Character
import rs.dusk.game.entity.character.player.data.Viewport

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
data class Player(val username : String, var session : Session, override var tile : Tile) : Character() {
	
	var removed = false
	
}