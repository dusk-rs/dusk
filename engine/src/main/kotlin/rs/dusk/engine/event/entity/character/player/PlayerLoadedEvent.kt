package rs.dusk.engine.event.entity.character.player

import rs.dusk.engine.event.EventCompanion
import rs.dusk.engine.model.entity.index.player.Player
import rs.dusk.engine.model.entity.index.player.PlayerEvent

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since June 25, 2020
 */
class PlayerLoadedEvent(override val player: Player) : PlayerEvent() {

    companion object : EventCompanion<PlayerLoadedEvent>

}