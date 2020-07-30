package rs.dusk.world.interact.player.display.tab

import rs.dusk.engine.client.send
import rs.dusk.engine.event.then
import rs.dusk.engine.event.where
import rs.dusk.network.rs.codec.game.encode.message.InterfaceSettingsMessage
import rs.dusk.world.interact.player.display.InterfaceInteraction

InterfaceInteraction where { name == "notes" } then {
    player.send(InterfaceSettingsMessage(id, 9, 0, 30, 2621470))
}