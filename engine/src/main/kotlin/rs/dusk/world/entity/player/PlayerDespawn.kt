package rs.dusk.world.entity.player

import rs.dusk.engine.event.EventCompanion
import rs.dusk.engine.model.entity.character.player.Player
import rs.dusk.engine.model.entity.character.player.PlayerEvent

data class PlayerDespawn(override val player: Player) : PlayerEvent() {
    companion object : EventCompanion<PlayerDespawn>
}