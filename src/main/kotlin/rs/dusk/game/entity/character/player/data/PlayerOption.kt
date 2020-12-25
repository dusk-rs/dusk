package rs.dusk.game.entity.character.player.data

import rs.dusk.core.event.character.PlayerEvent
import rs.dusk.engine.event.EventCompanion
import rs.dusk.game.entity.character.player.Player

data class PlayerOption(override val player: Player, val target: Player, val option: String, val optionId: Int) : PlayerEvent() {
	companion object : EventCompanion<PlayerOption>
}