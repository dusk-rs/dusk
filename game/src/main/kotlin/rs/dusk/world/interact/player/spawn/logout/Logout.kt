package rs.dusk.world.interact.player.spawn.logout

import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.entity.character.player.PlayerEvent
import rs.dusk.engine.event.EventCompanion

data class Logout(override val player: Player) : PlayerEvent() {
    companion object : EventCompanion<Logout>
}