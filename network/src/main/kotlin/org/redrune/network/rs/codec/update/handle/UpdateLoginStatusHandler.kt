package org.redrune.network.rs.codec.update.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import org.redrune.core.network.codec.message.MessageHandler
import org.redrune.core.network.model.session.getSession
import org.redrune.network.rs.codec.update.decode.UpdateLoginStatusMessage
import org.redrune.network.rs.codec.update.encode.UpdateRegistryResponse
import org.redrune.utility.constants.network.LoginResponseCodes

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
class UpdateLoginStatusHandler : MessageHandler<UpdateLoginStatusMessage>() {

    private val logger = InlineLogger()

    override fun handle(ctx: ChannelHandlerContext, msg: UpdateLoginStatusMessage) {
        val (login, value) = msg
        if (value != 0) {
            ctx.writeAndFlush(UpdateRegistryResponse(LoginResponseCodes.BAD_SESSION_ID))
            logger.warn { "Invalid login id ${ctx.channel().getSession().getIp()} $value" }
            return
        }

        logger.info { "Client is ${if (login) "logged in" else "logged out"} ${ctx.channel().getSession().getIp()}" }
    }
}