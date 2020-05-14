package rs.dusk.engine.model.entity.index.player

import rs.dusk.engine.model.entity.index.Changes
import rs.dusk.engine.model.entity.index.Indexed
import rs.dusk.engine.model.entity.index.Movement
import rs.dusk.engine.model.entity.index.update.Visuals
import rs.dusk.engine.model.entity.index.update.visual.player.getAppearance
import rs.dusk.engine.model.world.Tile
import rs.dusk.engine.view.Viewport

/**
 * A player controlled by client or bot
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 28, 2020
 */
data class Player(
    @Transient override var index: Int = -1,
    override var id: Int = -1,
    override var tile: Tile = Tile(0),
    @Transient val viewport: Viewport = Viewport(),
    @Transient override val visuals: Visuals = Visuals(),
    @Transient override val changes: Changes = Changes(),
    @Transient override val movement: Movement = Movement(delta = tile)
) : Indexed {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Player
        return index == other.index
    }

    override fun hashCode(): Int {
        return index
    }

    override fun toString(): String {
        return "Player(${getAppearance().displayName}, index=$index, tile=$tile)"
    }
}