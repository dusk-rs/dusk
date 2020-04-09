package org.redrune.game.entity.npc

import org.redrune.engine.Startup
import org.redrune.engine.data.file.FileLoader
import org.redrune.engine.entity.factory.NPCFactory
import org.redrune.engine.event.then
import org.redrune.engine.model.Direction
import org.redrune.utility.inject

val npcs: NPCFactory by inject()
val files: FileLoader by inject()

data class Spawn(val id: Int, val x: Int, val y: Int, val plane: Int = 0, val direction: Direction = Direction.NONE)

Startup then {
    val spawns = files.load<Array<Spawn>>("./data/spawns.yml")
    spawns?.forEach {
        npcs.spawn(it.id, it.x, it.y, it.plane, it.direction)
    }
}