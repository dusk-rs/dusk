package rs.dusk.world.interact.entity.obj

import rs.dusk.engine.client.ui.open
import rs.dusk.engine.entity.obj.ObjectOption
import rs.dusk.engine.event.then
import rs.dusk.engine.event.where

ObjectOption where {
    val optionLowercase = option?.toLowerCase()
    obj.def.name == "Counter" && (optionLowercase.equals("use") || optionLowercase
        .equals("use-quickly"))
} then {
    player.open("bank")
}
