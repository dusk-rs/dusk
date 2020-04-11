package org.redrune.network.rs.codec.update.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import org.redrune.core.network.codec.message.MessageHandler
import org.redrune.network.rs.codec.update.decode.UpdateDisconnectionMessage

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
class UpdateDisconnectionMessageHandler : MessageHandler<UpdateDisconnectionMessage>() {

    private val logger = InlineLogger()
    override fun handle(ctx: ChannelHandlerContext, msg: UpdateDisconnectionMessage) {
        if (msg.value != 0) {
            logger.warn { "Invalid disconnect id" }
        }
        ctx.close()
    }
}