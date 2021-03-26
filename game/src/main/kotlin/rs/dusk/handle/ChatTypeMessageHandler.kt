package rs.dusk.handle

import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.model.session.getSession
import rs.dusk.engine.client.Sessions
import rs.dusk.network.rs.codec.game.GameMessageHandler
import rs.dusk.network.rs.codec.game.decode.message.ChatTypeMessage
import rs.dusk.utility.inject

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since March 26, 2021
 */
class ChatTypeMessageHandler : GameMessageHandler<ChatTypeMessage>() {

    val sessions: Sessions by inject()

    override fun handle(ctx: ChannelHandlerContext, msg: ChatTypeMessage) {
        val session = ctx.channel().getSession()
        val player = sessions.get(session) ?: return
        val (type) = msg

        // TODO: set player chat type
    }

}