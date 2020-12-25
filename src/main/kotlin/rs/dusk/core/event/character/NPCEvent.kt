package rs.dusk.core.event.character

import rs.dusk.core.event.Event
import rs.dusk.game.entity.character.npc.NPC

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 31, 2020
 */
abstract class NPCEvent : Event<Unit>() {
	abstract val npc: NPC
}
