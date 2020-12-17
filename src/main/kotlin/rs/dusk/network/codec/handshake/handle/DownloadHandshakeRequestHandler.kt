package rs.dusk.network.codec.handshake.handle

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
import rs.dusk.network.codec.download.encode.message.DownloadServiceRequest
import rs.dusk.network.codec.handshake.HandshakeMessageHandler
import rs.dusk.network.codec.handshake.decode.message.DownloadServiceHandshakeRequest

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class DownloadHandshakeRequestHandler : HandshakeMessageHandler<DownloadServiceHandshakeRequest>() {
	
	private val clientMajorBuild = 667
	
	private val downloadCodec : DownloadCodec by inject()
	
	override fun handle(ctx : ChannelHandlerContext, msg : DownloadServiceHandshakeRequest) {
		val major = msg.major
		val response =
			if (major == clientMajorBuild) {
				DownloadServiceResponseCodes.JS5_RESPONSE_OK
			} else {
				DownloadServiceResponseCodes.JS5_RESPONSE_CONNECT_OUTOFDATE
			}
		
		println("major = $major, ours = $clientMajorBuild")
		
		val channel = ctx.channel()
		val pipeline = ctx.pipeline()
		
		pipeline.apply {
			replace("packet.decoder", SimplePacketDecoder())
			replace("message.decoder", OpcodeMessageDecoder())
			replace("message.reader", NetworkMessageReader())
			replace("message.encoder", GenericMessageEncoder())
			
			channel.setCodec(downloadCodec)
		}
		
		pipeline.writeAndFlush(DownloadServiceRequest(response))
		
	}
}