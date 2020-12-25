package rs.dusk.core.event.character

import rs.dusk.core.event.Event
import rs.dusk.game.entity.character.player.Player

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 31, 2020
 */
abstract class PlayerEvent : Event<Unit>() {
	abstract val player : Player
}