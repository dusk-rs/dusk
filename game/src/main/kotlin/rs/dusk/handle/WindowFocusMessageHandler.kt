package rs.dusk.handle

import io.netty.channel.ChannelHandlerContext
import rs.dusk.network.rs.codec.game.GameMessageHandler
import rs.dusk.network.rs.codec.game.decode.message.WindowFocusMessage

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since March 26, 2021
 */
class WindowFocusMessageHandler() : GameMessageHandler<WindowFocusMessage>() {

    override fun handle(ctx: ChannelHandlerContext, msg: WindowFocusMessage) {
        // TODO: store this as an attribute in the player
        val focused = msg.focused
    }


}