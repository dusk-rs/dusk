package rs.dusk.network.codec.game.handle

import io.netty.channel.ChannelHandlerContext
import rs.dusk.network.codec.game.GameMessageHandler
import rs.dusk.network.codec.game.decode.message.WorldListRefreshMessage
import rs.dusk.network.codec.game.encode.message.WorldListResponseMessage

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 22, 2020
 */
class WorldListRefreshMessageHandler : GameMessageHandler<WorldListRefreshMessage>() {

    override fun handle(ctx: ChannelHandlerContext, msg: WorldListRefreshMessage) {
        ctx.pipeline().writeAndFlush(WorldListResponseMessage(msg.crc == 0))
    }
}