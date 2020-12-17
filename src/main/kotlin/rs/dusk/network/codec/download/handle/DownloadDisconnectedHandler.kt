package rs.dusk.network.codec.download.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.session.getSession
import rs.dusk.network.codec.NetworkResponseCode.BadSessionId
import rs.dusk.network.codec.download.DownloadServiceMessageHandler
import rs.dusk.network.codec.download.decode.message.DownloadDisconnected
import rs.dusk.network.codec.download.encode.message.DownloadResponseCode

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class DownloadDisconnectedHandler : DownloadServiceMessageHandler<DownloadDisconnected>() {
	
	private val logger = InlineLogger()
	
	override fun handle(ctx : ChannelHandlerContext, msg : DownloadDisconnected) {
		if (msg.value != 0) {
			ctx.writeAndFlush(DownloadResponseCode(BadSessionId))
			logger.info { "Invalid disconnect id"  }
			return
		}
		logger.trace { "Disconnection complete ${ctx.channel().getSession().getIp()}" }
	}
}