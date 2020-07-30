package rs.dusk.engine.model.entity.obj

import rs.dusk.cache.definition.data.ObjectDefinition
import rs.dusk.cache.definition.decoder.ObjectDecoder
import rs.dusk.engine.model.entity.Entity
import rs.dusk.engine.model.entity.Size
import rs.dusk.engine.model.entity.character.player.Player
import rs.dusk.engine.model.entity.character.update.visual.player.name
import rs.dusk.engine.model.entity.item.offset
import rs.dusk.engine.model.map.Tile
import rs.dusk.engine.model.map.chunk.ChunkBatcher
import rs.dusk.network.rs.codec.game.encode.message.ObjectAnimationSpecificMessage
import rs.dusk.utility.get

/**
 * Interactive Object
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 28, 2020
 */
data class GameObject(
    override val id: Int,
    override var tile: Tile,
    val type: Int,
    val rotation: Int,
    val owner: String? = null
) : Entity {
    val def: ObjectDefinition
        get() = get<ObjectDecoder>().getSafe(id)

    val size: Size by lazy { Size(def.sizeX, def.sizeY) }

    fun visible(player: Player) = owner == null || owner == player.name
}

fun GameObject.animate(id: Int) {
    get<ChunkBatcher>().update(tile.chunk, ObjectAnimationSpecificMessage(tile.offset(), id, type, rotation))
}