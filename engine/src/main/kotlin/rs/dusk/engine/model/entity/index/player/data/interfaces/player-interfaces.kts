import rs.dusk.engine.event.entity.character.player.PlayerLoadedEvent
import rs.dusk.engine.event.on
import rs.dusk.engine.event.then
import rs.dusk.engine.model.entity.index.player.logic.InterfaceSystem
import rs.dusk.utility.inject

val interfaceSystem: InterfaceSystem by inject()

/**
 * Send the default interfaces to the player
 */
on<PlayerLoadedEvent> {
    then {
        interfaceSystem.sendGameFrame(player)
    }
}