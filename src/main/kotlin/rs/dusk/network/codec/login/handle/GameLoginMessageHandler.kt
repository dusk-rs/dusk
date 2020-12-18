package rs.dusk.network.codec.login.handle

import inject
import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.buffer.cipher.isaac.IsaacKeyPair
import rs.dusk.core.network.channel.setCodec
import rs.dusk.core.network.message.encode.GenericMessageEncoder
import rs.dusk.core.network.packet.access.PacketBuilder
import rs.dusk.core.network.packet.decode.RS2PacketDecoder
import rs.dusk.core.utility.replace
import rs.dusk.game.entity.character.player.Player
import rs.dusk.network.codec.game.GameCodec
import rs.dusk.network.codec.game.encode.message.MapRegionMessage
import rs.dusk.network.codec.login.LoginMessageHandler
import rs.dusk.network.codec.login.decode.message.GameLoginMessage
import rs.dusk.network.codec.login.encode.message.GameLoginDetails

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17th, 2020
 */
class GameLoginMessageHandler : LoginMessageHandler<GameLoginMessage>() {
	
	private val gameCodec : GameCodec by inject()
	
	override fun handle(ctx : ChannelHandlerContext, msg : GameLoginMessage) {
		val pipeline = ctx.pipeline()
		val keyPair = IsaacKeyPair(msg.isaacKeys)
		
		pipeline.writeAndFlush(GameLoginDetails(2, 1, msg.username))
		
		ctx.channel().setCodec(gameCodec)
		
		val player = Player(msg.username)
		val xteas = intArrayOf(
			154360370,
			-2039884462,
			189202037,
			-1449247101
		)
		
		pipeline.replace("packet.decoder", RS2PacketDecoder(keyPair.inCipher))
		pipeline.replace("message.encoder", GenericMessageEncoder(PacketBuilder(keyPair.outCipher)))
		
		pipeline.writeAndFlush(MapRegionMessage(104, true, player.tile.getChunkX(), player.tile.getChunkY(), xteas))
	}
	
}