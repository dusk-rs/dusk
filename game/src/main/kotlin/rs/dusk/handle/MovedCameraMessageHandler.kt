package rs.dusk.handle

import io.netty.channel.ChannelHandlerContext
import rs.dusk.network.rs.codec.game.GameMessageHandler
import rs.dusk.network.rs.codec.game.decode.message.MovedCameraMessage

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since March 26, 2021
 */
class MovedCameraMessageHandler : GameMessageHandler<MovedCameraMessage>() {

    override fun handle(ctx: ChannelHandlerContext, msg: MovedCameraMessage) {
        // TODO: figure out what this is used for
        val (pitch, yaw) = msg
    }

}