package rs.dusk.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.model.session.getSession
import rs.dusk.engine.client.Sessions
import rs.dusk.network.rs.codec.game.GameMessageHandler
import rs.dusk.network.rs.codec.game.decode.message.PublicMessage
import rs.dusk.utility.inject

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since March 26, 2021
 */
class PublicMessageHandler : GameMessageHandler<PublicMessage>() {

    override fun handle(ctx: ChannelHandlerContext, msg: PublicMessage) {
        val session = ctx.channel().getSession()
        val player = sessions.get(session) ?: return
        val (message, effects) = msg

        // TODO: finish public chat

    }

    companion object {

        private val sessions: Sessions by inject()

    }


}