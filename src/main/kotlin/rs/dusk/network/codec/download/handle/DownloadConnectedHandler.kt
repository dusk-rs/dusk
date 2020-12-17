package rs.dusk.network.codec.download.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.session.getSession
import rs.dusk.network.codec.NetworkResponseCode
import rs.dusk.network.codec.download.DownloadServiceMessageHandler
import rs.dusk.network.codec.download.decode.message.DownloadConnected
import rs.dusk.network.codec.download.encode.message.DownloadResponseCode

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class DownloadConnectedHandler : DownloadServiceMessageHandler<DownloadConnected>() {
	
	private val logger = InlineLogger()
	
	override fun handle(ctx: ChannelHandlerContext, msg: DownloadConnected) {
		if (msg.value != 3) {
			ctx.writeAndFlush(DownloadResponseCode(NetworkResponseCode.BadSessionId))
			logger.info { "Invalid connection id ${ctx.channel().getSession().getIp()} ${msg.value}" }
			return
		}
		logger.trace { "Connection complete ${ctx.channel().getSession().getIp()}" }
	}
}