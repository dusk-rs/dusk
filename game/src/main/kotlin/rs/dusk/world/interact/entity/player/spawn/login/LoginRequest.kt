package rs.dusk.world.interact.entity.player.spawn.login

import com.github.michaelbull.logging.InlineLogger
import rs.dusk.core.network.model.session.Session
import rs.dusk.engine.event.Event
import rs.dusk.engine.event.EventCompanion
import rs.dusk.network.rs.codec.login.decode.message.GameLoginMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 4th, 2020
 */
data class LoginRequest(
    val message: GameLoginMessage? = null,
    val session: Session? = null,
    val callback: ((LoginResponse) -> Unit)? = null
) : Event<Unit>() {

    val name = message!!.username

    private val logger = InlineLogger()

    fun respond(response: LoginResponse) {
        logger.trace { "LoginRequest[login=$this, response=response]" }
        callback?.invoke(response)
    }

    companion object : EventCompanion<LoginRequest>
}