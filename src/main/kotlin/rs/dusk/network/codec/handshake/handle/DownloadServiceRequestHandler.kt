package rs.dusk.network.codec.handshake.handle

import getProperty
import inject
import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.channel.setCodec
import rs.dusk.core.network.message.NetworkMessageReader
import rs.dusk.core.network.message.decode.OpcodeMessageDecoder
import rs.dusk.core.network.message.encode.GenericMessageEncoder
import rs.dusk.core.network.packet.decode.SimplePacketDecoder
import rs.dusk.core.utility.replace
import rs.dusk.network.codec.download.DownloadCodec
import rs.dusk.network.codec.download.DownloadServiceResponseCodes
import rs.dusk.network.codec.download.encode.message.DownloadServiceResponse
import rs.dusk.network.codec.handshake.HandshakeMessageHandler
import rs.dusk.network.codec.handshake.decode.message.DownloadServiceRequest

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class DownloadServiceRequestHandler : HandshakeMessageHandler<DownloadServiceRequest>() {
	
	private val downloadCodec : DownloadCodec by inject()
	private val clientMajorBuild: Int = getProperty("clientBuild")
	
	override fun handle(ctx : ChannelHandlerContext, msg : DownloadServiceRequest) {
		val pipeline = ctx.pipeline()
		
		val success = msg.major == clientMajorBuild
		val response =
			if (success) DownloadServiceResponseCodes.JS5_RESPONSE_OK else DownloadServiceResponseCodes.JS5_RESPONSE_CONNECT_OUTOFDATE
		
		pipeline.apply {
			replace("packet.decoder", SimplePacketDecoder())
			replace("message.decoder", OpcodeMessageDecoder())
			replace("message.reader", NetworkMessageReader())
			replace("message.encoder", GenericMessageEncoder())
			
			ctx.channel().setCodec(downloadCodec)
		}
		
		pipeline.writeAndFlush(DownloadServiceResponse(response))
		
	}
}