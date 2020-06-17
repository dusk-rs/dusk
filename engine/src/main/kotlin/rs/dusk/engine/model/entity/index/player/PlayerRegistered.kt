package rs.dusk.engine.model.entity.index.player

import rs.dusk.engine.event.EventCompanion

data class PlayerRegistered(override val player: Player) : PlayerEvent() {
    companion object : EventCompanion<PlayerRegistered>
}