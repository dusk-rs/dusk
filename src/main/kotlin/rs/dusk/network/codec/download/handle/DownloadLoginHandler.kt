package rs.dusk.network.codec.download.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.session.getSession
import rs.dusk.network.codec.NetworkResponseCode.BadSessionId
import rs.dusk.network.codec.download.DownloadServiceMessageHandler
import rs.dusk.network.codec.download.decode.message.DownloadLogin
import rs.dusk.network.codec.download.encode.message.DownloadResponseCode

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class DownloadLoginHandler : DownloadServiceMessageHandler<DownloadLogin>() {
	
	private val logger = InlineLogger()
	
	override fun handle(ctx : ChannelHandlerContext, msg : DownloadLogin) {
		val value = msg.value
		if (value != 0) {
			ctx.writeAndFlush(DownloadResponseCode(BadSessionId))
			logger.debug { "Invalid login id ${ctx.channel().getSession().getIp()} $value" }
			return
		}
		
		logger.info { "Client is logged in ${ctx.channel().getSession().getIp()}" }
	}
	
}