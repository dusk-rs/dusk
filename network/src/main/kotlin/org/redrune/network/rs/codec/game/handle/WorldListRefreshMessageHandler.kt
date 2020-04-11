package org.redrune.network.rs.codec.game.handle

import io.netty.channel.ChannelHandlerContext
import org.redrune.core.network.codec.message.MessageHandler
import org.redrune.network.rs.codec.game.decode.WorldListRefreshMessage

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 22, 2020
 */
class WorldListRefreshMessageHandler : MessageHandler<WorldListRefreshMessage>() {

    override fun handle(ctx: ChannelHandlerContext, msg: WorldListRefreshMessage) {
//        ctx.pipeline().writeAndFlush(WorldListResponseMessage(msg.crc == 0))
    }
}