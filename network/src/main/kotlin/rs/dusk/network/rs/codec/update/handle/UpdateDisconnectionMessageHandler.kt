package rs.dusk.network.rs.codec.update.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import rs.dusk.network.rs.codec.update.UpdateMessageHandler
import rs.dusk.network.rs.codec.update.decode.message.UpdateDisconnectionMessage

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since February 18, 2020
 */
class UpdateDisconnectionMessageHandler : UpdateMessageHandler<UpdateDisconnectionMessage>() {

    private val logger = InlineLogger()

    override fun handle(ctx: ChannelHandlerContext, msg: UpdateDisconnectionMessage) {
        if (msg.value != 0) {
            logger.debug { "Invalid disconnect id"  }
        }
        ctx.close()
    }
}