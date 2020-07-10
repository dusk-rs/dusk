package rs.dusk.engine.client.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.model.session.getSession
import rs.dusk.engine.client.Sessions
import rs.dusk.network.rs.codec.game.GameMessageHandler
import rs.dusk.network.rs.codec.game.decode.message.NPCOptionMessage
import rs.dusk.utility.inject

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 30, 2020
 */
class NPCOptionMessageHandler : GameMessageHandler<NPCOptionMessage>() {

    val logger = InlineLogger()
    val sessions: Sessions by inject()

    override fun handle(ctx: ChannelHandlerContext, msg: NPCOptionMessage) {
        val session = ctx.channel().getSession()
        sessions.send(session, msg)
    }

}