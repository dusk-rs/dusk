package rs.dusk.network.codec.login.handle

import io.netty.channel.ChannelHandlerContext
import rs.dusk.network.codec.game.GameCodec
import rs.dusk.network.codec.login.LoginMessageHandler
import rs.dusk.network.codec.login.decode.message.LobbyLoginMessage
import rs.dusk.network.codec.login.encode.message.LobbyConfigurationMessage

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
class LobbyLoginMessageHandler : LoginMessageHandler<LobbyLoginMessage>() {
	
	private val gameCodec : GameCodec by inject()
	
	override fun handle(ctx : ChannelHandlerContext, msg : LobbyLoginMessage) {
		val pipeline = ctx.pipeline()
		val keyPair = IsaacKeyPair(msg.isaacSeed)
		val channel = ctx.channel()
		
		pipeline.replace("message.encoder", RS2PacketDecoder(PacketBuilder(sized = true)))
		
		pipeline.writeAndFlush(
			LobbyConfigurationMessage(
				msg.username,
				ctx.channel().getSession().getIp(),
				System.currentTimeMillis()
			)
		)
		channel.setCodec(gameCodec)
		
		with(pipeline) {
			replace("packet.decoder", RS2PacketDecoder(keyPair.inCipher))
			replace("message.decoder", OpcodeMessageDecoder())
			replace("message.encoder", GenericMessageEncoder(PacketBuilder(keyPair.outCipher)))
		}
	}
}