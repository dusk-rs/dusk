package rs.dusk.engine.client.handle

import ch.qos.logback.core.net.server.Client
import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rs.dusk.core.io.crypto.IsaacKeyPair
import rs.dusk.core.network.codec.CodecRepository
import rs.dusk.core.network.codec.message.MessageReader
import rs.dusk.core.network.codec.message.decode.OpcodeMessageDecoder
import rs.dusk.core.network.codec.message.encode.GenericMessageEncoder
import rs.dusk.core.network.codec.packet.access.PacketBuilder
import rs.dusk.core.network.codec.packet.decode.RS2PacketDecoder
import rs.dusk.core.network.codec.setCodec
import rs.dusk.core.network.model.session.getSession
import rs.dusk.core.utility.replace
import rs.dusk.engine.client.session.Sessions
import rs.dusk.engine.event.EventBus
import rs.dusk.engine.event.entity.character.player.PlayerLoadedEvent
import rs.dusk.engine.model.entity.index.player.data.ClientSettings
import rs.dusk.engine.model.entity.index.update.visual.player.name
import rs.dusk.network.rs.codec.game.GameCodec
import rs.dusk.network.rs.codec.login.LoginCodec
import rs.dusk.network.rs.codec.login.LoginMessageHandler
import rs.dusk.network.rs.codec.login.decode.message.GameLoginMessage
import rs.dusk.network.rs.codec.login.encode.message.GameLoginDetails
import rs.dusk.utility.inject
import rs.dusk.world.entity.player.login.LoginList
import rs.dusk.world.entity.player.login.LoginResponse

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 18, 2020
 */
class GameLoginMessageHandler : LoginMessageHandler<GameLoginMessage>() {

    val logger = InlineLogger()
    val sessions: Sessions by inject()
    val login: LoginList by inject()
    val repository: CodecRepository by inject()
    val bus: EventBus by inject()

    override fun handle(ctx: ChannelHandlerContext, msg: GameLoginMessage) {
        val channel = ctx.channel()
        val pipeline = ctx.pipeline()
        val keyPair = IsaacKeyPair(msg.isaacKeys)

        channel.setCodec(repository.get(LoginCodec::class))
        pipeline.replace("message.encoder", GenericMessageEncoder(PacketBuilder(sized = true)))

        println(msg)
        GlobalScope.launch {
            val session = ctx.channel().getSession()
            when (val response = login.add(msg.username, session).await()) {
                LoginResponse.Full -> TODO()
                LoginResponse.Failure -> logger.warn { "Unable to load player '${msg.username}'." }
                is LoginResponse.Success -> {
                    val player = response.player

                    player.clientSettings = ClientSettings(msg.mode, msg.width, msg.height)

                    with(player) {
                        pipeline.writeAndFlush(GameLoginDetails(2, index, name))
                    }

                    with(pipeline) {
                        replace("packet.decoder", RS2PacketDecoder(keyPair.inCipher))
                        replace("message.decoder", OpcodeMessageDecoder())
                        replace("message.reader", MessageReader())
                        replace("message.encoder", GenericMessageEncoder(PacketBuilder(keyPair.outCipher)))
                    }

                    channel.setCodec(repository.get(GameCodec::class))
                    sessions.send(session, msg)

                    bus.emit(PlayerLoadedEvent(player))
                }
            }
        }
    }

}