package org.redrune.network.rs.codec.login.handle

import io.netty.channel.ChannelHandlerContext
import org.koin.core.qualifier.named
import org.redrune.core.network.codec.Codec
import org.redrune.core.network.codec.message.MessageDecoder
import org.redrune.core.network.codec.message.MessageEncoder
import org.redrune.core.network.codec.message.MessageHandler
import org.redrune.core.network.codec.message.handle.NetworkMessageHandler
import org.redrune.core.network.codec.packet.access.PacketBuilder
import org.redrune.core.network.codec.packet.decode.RS2PacketDecoder
import org.redrune.core.network.model.session.getSession
import org.redrune.core.tools.utility.replace
import org.redrune.network.ServerNetworkEventHandler
import org.redrune.network.rs.codec.login.decode.LobbyLoginMessage
import org.redrune.network.rs.codec.login.encode.LobbyConfigurationMessage
import org.redrune.network.rs.session.GameSession
import org.redrune.utility.crypto.cipher.IsaacKeyPair
import org.redrune.utility.inject

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
class LobbyLoginMessageHandler : MessageHandler<LobbyLoginMessage>() {

    private val loginCodec: Codec by inject(named("loginCodec"))
    private val gameCodec: Codec by inject(named("loginCodec"))

    override fun handle(ctx: ChannelHandlerContext, msg: LobbyLoginMessage) {
        val pipeline = ctx.pipeline()
        val keyPair = IsaacKeyPair(msg.isaacSeed)
        pipeline.replace("message.encoder", MessageEncoder(loginCodec, PacketBuilder(sized = true)))

        println("issac seed = ${msg.isaacSeed.contentToString()}")

        pipeline.writeAndFlush(
            LobbyConfigurationMessage(
                msg.username,
                ctx.channel().getSession().getIp(),
                System.currentTimeMillis()
            )
        )

        with(pipeline) {
            replace("packet.decoder", RS2PacketDecoder(keyPair.inCipher, gameCodec))
            replace("message.decoder", MessageDecoder(gameCodec))
            replace(
                "message.handler", NetworkMessageHandler(
                    gameCodec,
                    ServerNetworkEventHandler(GameSession(channel()))
                )
            )
            replace("message.encoder", MessageEncoder(gameCodec, PacketBuilder(keyPair.outCipher)))
        }
    }
}