package rs.dusk.engine.client.viewport

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import rs.dusk.engine.client.session.Sessions
import rs.dusk.engine.model.engine.task.EngineTask
import rs.dusk.engine.model.entity.index.Character
import rs.dusk.engine.model.entity.index.TrackingSet
import rs.dusk.engine.model.entity.index.npc.NPCs
import rs.dusk.engine.model.entity.index.player.Players
import rs.dusk.engine.model.entity.list.PooledMapList
import rs.dusk.engine.model.world.Tile
import rs.dusk.utility.inject

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 17, 2020
 */
class ViewportTask : EngineTask {

    val players: Players by inject()
    val npcs: NPCs by inject()
    val sessions: Sessions by inject()

    override fun run() = runBlocking {
        coroutineScope {
            players.forEach { player ->
                if (!sessions.contains(player)) {
                    return@forEach
                }
                launch {
                    update(player.tile, players, player.viewport.players, LOCAL_PLAYER_CAP, player)
                }
                launch {
                    update(player.tile, npcs, player.viewport.npcs, LOCAL_NPC_CAP, null)
                }
            }
        }
    }

    /**
     * Updates a tracking set quickly, or precisely when local entities exceeds [cap]
     */
    fun <T : Character> update(tile: Tile, list: PooledMapList<T>, set: TrackingSet<T>, cap: Int, self: T?) {
        set.prep(self)
        val entityCount = nearbyEntityCount(list, tile)
        if (entityCount >= cap) {
            gatherByTile(tile, list, set, self)
        } else {
            gatherByChunk(tile, list, set, self)
        }
    }

    /**
     * Updates [set] precisely for when local entities exceeds maximum stopping at [TrackingSet.maximum]
     */
    fun <T : Character> gatherByTile(tile: Tile, list: PooledMapList<T>, set: TrackingSet<T>, self: T?) {
        Spiral.spiral(
            tile,
            VIEW_RADIUS
        ) { t ->
            val entities = list[t]
            if (entities != null && !set.track(entities, self)) {
                return
            }
        }
    }

    /**
     * Updates [set] quickly by gathering all entities in local chunks stopping at [TrackingSet.maximum]
     */
    fun <T : Character> gatherByChunk(tile: Tile, list: PooledMapList<T>, set: TrackingSet<T>, self: T?) {
        val x = tile.x
        val y = tile.y
        Spiral.spiral(tile.chunk, 2) { chunk ->
            val entities = list[chunk]
            if (entities != null && !set.track(entities, self, x, y)) {
                return
            }
        }
    }

    /**
     * Total entities within radius of two chunks
     */
    fun nearbyEntityCount(list: PooledMapList<*>, tile: Tile): Int {
        var total = 0
        Spiral.spiral(tile.chunk, 2) { chunk ->
            val entities = list[chunk]
            if (entities != null) {
                total += entities.size
            }
        }
        return total
    }

    companion object {
        const val PLAYER_TICK_CAP = 40
        const val NPC_TICK_CAP = 40
        const val LOCAL_PLAYER_CAP = 255
        const val LOCAL_NPC_CAP = 255

        // View radius could be controlled per tracking set to give a nicer linear
        // expanding square when loading areas with more than max entities
        const val VIEW_RADIUS = 15
    }
}