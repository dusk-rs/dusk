package rs.dusk.network.codec.login.handle

import inject
import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.buffer.cipher.isaac.IsaacKeyPair
import rs.dusk.core.network.channel.setCodec
import rs.dusk.core.network.message.encode.GenericMessageEncoder
import rs.dusk.core.network.packet.access.PacketBuilder
import rs.dusk.core.network.packet.decode.RS2PacketDecoder
import rs.dusk.core.network.session.Session
import rs.dusk.core.utility.replace
import rs.dusk.game.entity.character.player.Player
import rs.dusk.game.entity.registry.PlayerRegistry
import rs.dusk.network.codec.game.GameCodec
import rs.dusk.network.codec.login.LoginMessageHandler
import rs.dusk.network.codec.login.decode.message.GameLoginMessage
import rs.dusk.network.codec.login.encode.message.GameLoginDetails

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17th, 2020
 */
class GameLoginMessageHandler : LoginMessageHandler<GameLoginMessage>() {
	
	private val gameCodec : GameCodec by inject()
	
	private val registry : PlayerRegistry by inject()
	
	override fun handle(ctx : ChannelHandlerContext, msg : GameLoginMessage) {
		val pipeline = ctx.pipeline()
		val keyPair = IsaacKeyPair(msg.isaacKeys)
		
		pipeline.replace("message.encoder", GenericMessageEncoder(PacketBuilder(sized = true)))
		
		// login details must be sent in the login codec
		pipeline.writeAndFlush(GameLoginDetails(2, 1, msg.username))
		
		// swap to game codec to start game
		ctx.channel().setCodec(gameCodec)
		
		// use isaac ciphers
		pipeline.replace("packet.decoder", RS2PacketDecoder(keyPair.inCipher))
		pipeline.replace("message.encoder", GenericMessageEncoder(PacketBuilder(keyPair.outCipher)))
		
		val player = Player(msg.username, Session(ctx.channel()))
		
		registry.register(player)
	}
	
}