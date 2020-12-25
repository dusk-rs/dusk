package rs.dusk.game.entity.character.npc

import rs.dusk.core.map.Tile
import rs.dusk.game.entity.Size
import rs.dusk.game.entity.character.Character

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 25, 2020
 */
data class NPC(val id : Int, override var tile : Tile = Tile.EMPTY, override var size : Size = Size.TILE) :
	Character() {
}