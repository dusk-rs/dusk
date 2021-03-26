package rs.dusk.network.rs.codec.game.handle

import io.netty.channel.ChannelHandlerContext
import rs.dusk.network.rs.codec.game.GameMessageHandler
import rs.dusk.network.rs.codec.game.decode.message.PingMessage

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since March 26, 2021
 */
class PingMessageHandler : GameMessageHandler<PingMessage>() {

    override fun handle(ctx: ChannelHandlerContext, msg: PingMessage) {

    }

}