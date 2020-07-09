package rs.dusk.engine.client.handle

import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.model.session.getSession
import rs.dusk.engine.client.session.Sessions
import rs.dusk.engine.model.entity.index.player.logic.InterfaceSystem
import rs.dusk.network.rs.codec.game.GameMessageHandler
import rs.dusk.network.rs.codec.game.decode.message.ScreenChangeMessage
import rs.dusk.utility.inject

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 18, 2020
 */
class ScreenChangeMessageHandler : GameMessageHandler<ScreenChangeMessage>() {

    val sessions: Sessions by inject()

    private val interfaceSystem: InterfaceSystem by inject()

    override fun handle(ctx: ChannelHandlerContext, msg: ScreenChangeMessage) {
        val session = ctx.channel().getSession()
        val player = sessions.get(session) ?: throw IllegalStateException("Unable to find player by session")
        interfaceSystem.sendGameFrame(player)
    }

}