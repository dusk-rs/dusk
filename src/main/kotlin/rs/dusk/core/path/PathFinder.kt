package rs.dusk.core.path

import org.koin.dsl.module
import rs.dusk.core.map.Tile
import rs.dusk.engine.action.ActionType.FloorItem
import rs.dusk.engine.path.Finder
import rs.dusk.engine.path.PathResult
import rs.dusk.engine.path.PathResult.Success.Complete
import rs.dusk.engine.path.TargetStrategy
import rs.dusk.engine.path.find.AxisAlignment
import rs.dusk.engine.path.find.BreadthFirstSearch
import rs.dusk.engine.path.find.DirectDiagonalSearch
import rs.dusk.engine.path.find.DirectSearch
import rs.dusk.engine.path.strat.EntityTileTargetStrategy
import rs.dusk.engine.path.strat.TileTargetStrategy
import rs.dusk.game.entity.character.player.Player

val pathFindModule = module {
    single { DirectSearch() }
    single { DirectDiagonalSearch() }
    single { AxisAlignment() }
    single { BreadthFirstSearch() }
    single { PathFinder(get(), get(), get(), get()) }
}

/**
 * Determines the correct strategy to use to reach a target [Entity] or [Tile]
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 21, 2020
 */
class PathFinder(
    private val aa: AxisAlignment,
    private val ds: DirectSearch,
    private val dd: DirectDiagonalSearch,
    private val bfs: BreadthFirstSearch
) {

    fun find(source: Player, tile: Tile, smart: Boolean = true): PathResult {
        val strategy = getStrategy(tile)
        return find(source, strategy, smart)
    }

    fun find(source: Player, target: Character, smart: Boolean = true): PathResult {
        return find(source, getEntityStrategy(target), smart)
    }

    fun find(source: Player, strategy: TargetStrategy, smart: Boolean = true): PathResult {
        if (strategy.reached(source.tile, source.size)) {
            return Complete(source.tile)
        }
        val finder = getFinder(source, smart)
        return finder.find(source.tile, source.size, source.movement, strategy, source.movement.traversal)
    }

    fun getFinder(source: Player, smart: Boolean): Finder {
        return if (source is Player) {
            if (smart) bfs else dd
        } else {
            aa
        }
    }

    companion object {
        @Throws(IllegalArgumentException::class)
        fun getStrategy(any: Any): TargetStrategy {
            return when (any) {
                is Tile -> TileTargetStrategy(any)
                is Player -> getEntityStrategy(any)
                else -> throw IllegalArgumentException("No target strategy found for $any")
            }
        }

        fun getEntityStrategy(entity: Player): TargetStrategy {
            return when (entity) {
              /*  is Character -> entity.interactTarget
                is GameObject -> entity.interactTarget
                is FloorItem  -> entity.interactTarget*/
                else          -> EntityTileTargetStrategy(entity)
            }
        }
    }
}