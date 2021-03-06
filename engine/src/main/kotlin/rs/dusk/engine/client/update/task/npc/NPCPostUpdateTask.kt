package rs.dusk.engine.client.update.task.npc

import rs.dusk.engine.entity.character.npc.NPC
import rs.dusk.engine.entity.character.npc.NPCs
import rs.dusk.engine.event.Priority.NPC_UPDATE_FINISHED
import rs.dusk.engine.tick.task.EntityTask

/**
 * Resets non-persistent changes
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class NPCPostUpdateTask(override val entities: NPCs) : EntityTask<NPC>(NPC_UPDATE_FINISHED) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun runAsync(npc: NPC) {
        npc.movement.reset()
        npc.visuals.aspects.forEach { (_, visual) ->
            visual.reset(npc)
        }
    }

}