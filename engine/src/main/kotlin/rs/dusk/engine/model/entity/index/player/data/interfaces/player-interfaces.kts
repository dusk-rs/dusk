import rs.dusk.engine.event.entity.character.player.PlayerLoadedEvent
import rs.dusk.engine.event.on
import rs.dusk.engine.event.then
import rs.dusk.engine.model.entity.index.player.logic.interfaces.InterfaceSystem
import rs.dusk.utility.inject

val interfaceSystem: InterfaceSystem by inject()

on<PlayerLoadedEvent> {
    then {
        interfaceSystem.login(player)
    }
}