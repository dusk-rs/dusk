
import rs.dusk.engine.entity.character.update.visual.player.name
import rs.dusk.engine.event.Priority
import rs.dusk.engine.event.priority
import rs.dusk.engine.event.then
import rs.dusk.engine.tick.TickInput
import rs.dusk.utility.inject
import rs.dusk.world.interact.entity.player.spawn.login.LoginQueue
import rs.dusk.world.interact.entity.player.spawn.login.LoginRequest
import rs.dusk.world.interact.entity.player.spawn.logout.Logout

val loginQueue: LoginQueue by inject()

TickInput priority Priority.LOGIN_QUEUE then {
    loginQueue.tick()
}

LoginRequest then {
    loginQueue.add(this)
}

Logout then {
    loginQueue.remove(player.name)
}