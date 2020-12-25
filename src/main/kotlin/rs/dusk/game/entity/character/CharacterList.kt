package rs.dusk.game.entity.character

import org.koin.dsl.module
import rs.dusk.core.map.Tile
import rs.dusk.game.entity.character.npc.NPCs
import rs.dusk.game.entity.character.player.Players

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 28, 2020
 */
interface CharacterList<T : Character> {
	
	operator fun get(hash : Int) : Set<T?>?
	
	operator fun get(tile : Tile) = get(tile.id)
	
	operator fun get(x : Int, y : Int, plane : Int = 0) = get(Tile(x, y, plane))
	
	fun add(hash : Int, entity : T) : Boolean
	
	fun add(tile : Tile, entity : T) = add(tile.id, entity)
	
	fun add(x : Int, y : Int, plane : Int = 0, entity : T) = add(Tile(x, y, plane), entity)
	
	fun remove(hash : Int, entity : T) : Boolean
	
	fun remove(tile : Tile, entity : T) = remove(tile.id, entity)
	
	fun remove(x : Int, y : Int, plane : Int = 0, entity : T) = remove(Tile(x, y, plane), entity)
	
	fun forEach(action : (T) -> Unit)
	
	operator fun set(hash : Int, entity : T) = add(hash, entity)
	
	operator fun set(tile : Tile, entity : T) = add(tile, entity)
	
	operator fun set(x : Int, y : Int, plane : Int = 0, entity : T) = add(x, y, plane, entity)
}

const val MAX_PLAYERS = 0x800// 2048
const val MAX_NPCS = 0x8000// 32768

val entityListModule = module {
	single { NPCs() }
	single { Players() }
//	single { Objects() }
//	single { FloorItems() }
//	single { Projectiles() }
//	single { Graphics() }
}