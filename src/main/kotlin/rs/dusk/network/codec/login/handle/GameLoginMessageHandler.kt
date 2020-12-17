package rs.dusk.network.codec.login.handle

import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.network.buffer.cipher.isaac.IsaacKeyPair
import rs.dusk.core.network.message.encode.GenericMessageEncoder
import rs.dusk.core.network.packet.access.PacketBuilder
import rs.dusk.core.network.packet.decode.RS2PacketDecoder
import rs.dusk.core.utility.replace
import rs.dusk.network.codec.login.LoginMessageHandler
import rs.dusk.network.codec.login.decode.message.GameLoginMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17th, 2020
 */
class GameLoginMessageHandler : LoginMessageHandler<GameLoginMessage>() {
	
	override fun handle(ctx : ChannelHandlerContext, msg : GameLoginMessage) {
		val pipeline = ctx.pipeline()
		val keyPair = IsaacKeyPair(msg.isaacKeys)
		
		/*
            if (response is LoginResponse.Success) {
                val player = response.player
                pipeline.writeAndFlush(GameLoginDetails(2, player.index, msg.username))

                with(pipeline) {
                    replace("packet.decoder", RS2PacketDecoder(keyPair.inCipher))
                    replace("message.decoder", OpcodeMessageDecoder())
                    replace("message.reader", MessageReader())
                    replace("message.encoder", GenericMessageEncoder(PacketBuilder(keyPair.outCipher)))
                }

                executor.sync {
                    channel.setCodec(repository.get(GameCodec::class))

                    bus.emit(RegionLogin(player))
                    bus.emit(PlayerRegistered(player))
                    player.start()
                    bus.emit(Registered(player))
                }
            } else {
                pipeline.writeAndFlush(GameLoginConnectionResponseMessage(response.code))
            }
            
		 */
		
		pipeline.replace("packet.decoder", RS2PacketDecoder(keyPair.inCipher))
		pipeline.replace("message.encoder", GenericMessageEncoder(PacketBuilder(keyPair.outCipher)))
	}
	
}