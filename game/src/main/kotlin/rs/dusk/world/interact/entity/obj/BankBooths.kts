package rs.dusk.world.interact.entity.obj

import rs.dusk.engine.client.ui.open
import rs.dusk.engine.entity.obj.ObjectOption
import rs.dusk.engine.event.then
import rs.dusk.engine.event.where

ObjectOption where {
    obj.def.name == "Counter" && validOptions.contains(option?.toLowerCase())
} then {
    player.open("bank")
}

// the list of valid options that should open the player's bank
val validOptions = listOf("use", "use-quickly")