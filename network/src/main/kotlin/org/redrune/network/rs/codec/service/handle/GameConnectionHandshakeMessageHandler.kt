package org.redrune.network.rs.codec.service.handle

import io.netty.channel.ChannelHandlerContext
import org.koin.core.qualifier.named
import org.redrune.core.network.codec.Codec
import org.redrune.core.network.codec.message.MessageDecoder
import org.redrune.core.network.codec.message.MessageEncoder
import org.redrune.core.network.codec.message.MessageHandler
import org.redrune.core.network.codec.message.handle.NetworkMessageHandler
import org.redrune.core.network.codec.packet.access.PacketBuilder
import org.redrune.core.network.codec.packet.decode.SimplePacketDecoder
import org.redrune.core.tools.utility.replace
import org.redrune.network.ServerNetworkEventHandler
import org.redrune.network.rs.codec.login.encode.LoginConnectionResponseMessage
import org.redrune.network.rs.codec.service.decode.GameConnectionHandshakeMessage
import org.redrune.network.rs.session.LoginSession
import org.redrune.utility.inject

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
class GameConnectionHandshakeMessageHandler : MessageHandler<GameConnectionHandshakeMessage>() {

    private val loginCodec: Codec by inject(named("loginCodec"))

    override fun handle(ctx: ChannelHandlerContext, msg: GameConnectionHandshakeMessage) {
        val pipeline = ctx.pipeline()
        pipeline.apply {
            replace("packet.decoder", SimplePacketDecoder(loginCodec))
            replace("message.decoder", MessageDecoder(loginCodec))
            replace(
                "message.handler", NetworkMessageHandler(
                    loginCodec,
                    ServerNetworkEventHandler(LoginSession(channel()))
                )
            )
            replace("message.encoder", MessageEncoder(loginCodec, PacketBuilder()))
        }
        ctx.pipeline().writeAndFlush(LoginConnectionResponseMessage(0))
    }

}