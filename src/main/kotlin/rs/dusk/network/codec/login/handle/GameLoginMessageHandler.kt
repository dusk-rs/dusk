package rs.dusk.network.codec.login.handle

import inject
import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.channel.setCodec
import rs.dusk.network.codec.game.GameCodec
import rs.dusk.network.codec.login.LoginMessageHandler
import rs.dusk.network.codec.login.decode.message.GameLoginMessage
import rs.dusk.network.codec.login.encode.message.GameLoginConnectionResponseMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17th, 2020
 */
class GameLoginMessageHandler : LoginMessageHandler<GameLoginMessage>() {
	
	private val gameCodec : GameCodec by inject()
	
	override fun handle(ctx : ChannelHandlerContext, msg : GameLoginMessage) {
		val pipeline = ctx.pipeline()
		
		pipeline.writeAndFlush(GameLoginConnectionResponseMessage(7))
		
		ctx.channel().setCodec(gameCodec)
	}
	
}