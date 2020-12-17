package rs.dusk.network.codec.handshake.handle

import inject
import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.channel.setCodec
import rs.dusk.network.codec.handshake.HandshakeMessageHandler
import rs.dusk.network.codec.handshake.decode.message.GameServiceRequest
import rs.dusk.network.codec.login.LoginCodec
import rs.dusk.network.codec.login.encode.message.LobbyLoginConnectionResponseMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class GameServiceRequestHandler : HandshakeMessageHandler<GameServiceRequest>() {
	
	private val logicCodec : LoginCodec by inject()
	
	override fun handle(ctx : ChannelHandlerContext, msg : GameServiceRequest) {
		ctx.channel().setCodec(logicCodec)
		ctx.pipeline().writeAndFlush(LobbyLoginConnectionResponseMessage(0))
	}
}