package rs.dusk.handle

import io.netty.channel.ChannelHandlerContext
import rs.dusk.network.rs.codec.game.GameMessageHandler
import rs.dusk.network.rs.codec.game.decode.message.MovedMouseMessage

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since March 26, 2021
 */
class MovedMouseMessageHandler : GameMessageHandler<MovedMouseMessage>() {

    override fun handle(ctx: ChannelHandlerContext, msg: MovedMouseMessage) {

    }

}