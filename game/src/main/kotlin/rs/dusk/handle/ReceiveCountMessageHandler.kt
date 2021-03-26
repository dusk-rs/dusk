package rs.dusk.handle

import io.netty.channel.ChannelHandlerContext
import rs.dusk.network.rs.codec.game.GameMessageHandler
import rs.dusk.network.rs.codec.game.decode.message.ReceiveCountMessage

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since March 26, 2021
 */
class ReceiveCountMessageHandler : GameMessageHandler<ReceiveCountMessage>() {

    override fun handle(ctx: ChannelHandlerContext, msg: ReceiveCountMessage) {
        // TODO: find out what this is used for
        val count = msg.count
    }

}