package rs.dusk.engine.path

import org.koin.dsl.module
import rs.dusk.engine.model.entity.Entity
import rs.dusk.engine.model.entity.Size
import rs.dusk.engine.model.entity.index.Indexed
import rs.dusk.engine.model.entity.index.player.Player
import rs.dusk.engine.model.entity.item.FloorItem
import rs.dusk.engine.model.entity.obj.Location
import rs.dusk.engine.model.world.Tile
import rs.dusk.engine.model.world.map.collision.Collisions
import rs.dusk.engine.path.find.AxisAlignment
import rs.dusk.engine.path.find.BreadthFirstSearch
import rs.dusk.engine.path.target.*
import rs.dusk.engine.path.traverse.LargeTraversal
import rs.dusk.engine.path.traverse.MediumTraversal
import rs.dusk.engine.path.traverse.SmallTraversal

val pathFindModule = module {
    single { AxisAlignment(get()) }
    single { BreadthFirstSearch() }
    single { PathFinder(get(), get(), get(), get(), get()) }
}

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 21, 2020
 */
class PathFinder(
    private val collisions: Collisions,
    private val aa: AxisAlignment,
    private val bfs: BreadthFirstSearch,
    private val small: SmallTraversal,
    private val medium: MediumTraversal
) {

    fun find(source: Indexed, tile: Tile): PathResult {
        val traverse = getTraversal(source.size)
        val strategy = TileTargetStrategy(tile = tile)
        val finder = getFinder(source)
        return finder.find(source.tile, source.size, source.movement, strategy, traverse)
    }

    fun find(source: Indexed, target: Entity): PathResult {
        val traverse = getTraversal(source.size)
        val strategy = getStrategy(target)
        val finder = getFinder(source)
        return finder.find(source.tile, source.size, source.movement, strategy, traverse)
    }

    fun getFinder(source: Indexed): Finder {
        return if (source is Player) {
            bfs
        } else {
            aa
        }
    }

    fun getTraversal(size: Size) = when {
        size.width == 1 && size.height == 1 -> small
        size.width == 2 && size.height == 2 -> medium
        else -> LargeTraversal(size, collisions)
    }

    fun getStrategy(target: Entity) = when (target) {
        is Location -> when (target.type) {
            in 0..2, 9 -> WallTargetStrategy(
                collisions,
                tile = target.tile,
                size = target.size,
                rotation = target.rotation,
                type = target.type
            )
            in 3..8 -> DecorationTargetStrategy(
                collisions,
                tile = target.tile,
                size = target.size,
                rotation = target.rotation,
                type = target.type
            )
            10, 11, 22 -> RectangleTargetStrategy(
                collisions,
                tile = target.tile,
                size = target.size,
                blockFlag = target.def.blockFlag
            )
            else -> TileTargetStrategy(tile = target.tile)
        }
        is FloorItem -> PointTargetStrategy(
            tile = target.tile,
            size = target.size
        )
        is Indexed -> RectangleTargetStrategy(
            collisions,
            tile = target.tile,
            size = target.size
        )
        else -> TileTargetStrategy(tile = target.tile)
    }
}