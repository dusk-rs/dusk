package rs.dusk.game.entity.character.move

import rs.dusk.core.event.character.NPCEvent
import rs.dusk.core.map.Tile
import rs.dusk.engine.event.EventCompanion
import rs.dusk.game.entity.character.npc.NPC

data class NPCMoved(override val npc : NPC, val from : Tile, val to : Tile) : NPCEvent() {
	companion object : EventCompanion<NPCMoved>
}