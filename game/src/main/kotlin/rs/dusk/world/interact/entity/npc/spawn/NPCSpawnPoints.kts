package rs.dusk.world.interact.entity.npc.spawn

import rs.dusk.engine.entity.Direction
import rs.dusk.engine.event.EventBus
import rs.dusk.engine.event.then
import rs.dusk.engine.io.file.jackson.yaml.YamlIO
import rs.dusk.engine.map.Tile
import rs.dusk.engine.map.region.Region
import rs.dusk.engine.map.region.RegionLoaded
import rs.dusk.engine.tick.Startup
import rs.dusk.utility.getProperty
import rs.dusk.utility.inject

val yamlIO: YamlIO<NPCSpawnPoint> by inject()
val bus: EventBus by inject()

data class NPCSpawnPoint(val id: Int, val tile: Tile, val direction: Direction = Direction.NONE)

val spawns: MutableMap<Region, MutableList<NPCSpawnPoint>> = mutableMapOf()

Startup then {
    val path: String = getProperty("npcsPath")
    val points: Array<NPCSpawnPoint> = yamlIO.read(path)
    points.forEach { spawn ->
        val list = spawns.getOrPut(spawn.tile.region) { mutableListOf() }
        list.add(spawn)
    }
}

RegionLoaded then {
    val spawns = spawns[region] ?: return@then
    spawns.forEach { spawn ->
        val npc = bus.emit(
            NPCSpawn(
                spawn.id,
                spawn.tile,
                spawn.direction
            )
        )
    }
}