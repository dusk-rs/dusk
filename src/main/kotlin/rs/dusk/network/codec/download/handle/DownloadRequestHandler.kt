package rs.dusk.network.codec.download.handle

import com.github.michaelbull.logging.InlineLogger
import com.google.common.primitives.Ints
import inject
import io.netty.channel.ChannelHandlerContext
import rs.dusk.cache.Cache
import rs.dusk.network.codec.download.DownloadServiceMessageHandler
import rs.dusk.network.codec.download.decode.message.DownloadRequest
import rs.dusk.network.codec.download.encode.message.DownloadCacheTransfer

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class DownloadRequestHandler : DownloadServiceMessageHandler<DownloadRequest>() {
	
	private val logger = InlineLogger()
	private val cache : Cache by inject()
	
	override fun handle(ctx : ChannelHandlerContext, msg : DownloadRequest) {
		val (indexId, archiveId, priority) = msg
		val data = cache.getArchive(indexId, archiveId)
			?: return logger.debug { "Request $this was invalid - did not exist in cache" }
		val compression : Int = data[0].toInt() and 0xff
		val length = Ints.fromBytes(data[1], data[2], data[3], data[4])
		var attributes = compression
		if (!priority) {
			attributes = attributes or 0x80
		}
		
		ctx.pipeline().writeAndFlush(DownloadCacheTransfer(indexId, archiveId, data, compression, length, attributes))
	}
	
}