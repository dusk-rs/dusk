package org.redrune.network.rs.codec.service.handle

import io.netty.channel.ChannelHandlerContext
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent
import org.redrune.core.network.codec.Codec
import org.redrune.core.network.codec.message.MessageDecoder
import org.redrune.core.network.codec.message.MessageEncoder
import org.redrune.core.network.codec.message.MessageHandler
import org.redrune.core.network.codec.message.handle.NetworkMessageHandler
import org.redrune.core.network.codec.packet.access.PacketBuilder
import org.redrune.core.network.codec.packet.decode.SimplePacketDecoder
import org.redrune.core.tools.utility.replace
import org.redrune.network.ServerNetworkEventHandler
import org.redrune.network.rs.codec.service.decode.UpdateHandshakeMessage
import org.redrune.network.rs.codec.update.encode.UpdateVersionMessage
import org.redrune.network.rs.session.UpdateSession
import org.redrune.utility.constants.network.FileServerResponseCodes
import org.redrune.utility.inject

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
class UpdateHandshakeMessageHandler : MessageHandler<UpdateHandshakeMessage>() {

    private val clientMajorBuild = KoinJavaComponent.getKoin().getProperty<Int>("clientBuild")
    private val updateCodec: Codec by inject(named("updateCodec"))

    override fun handle(ctx: ChannelHandlerContext, msg: UpdateHandshakeMessage) {
        val major = msg.major
        val response =
            if (major == clientMajorBuild) {
                FileServerResponseCodes.JS5_RESPONSE_OK
            } else {
                FileServerResponseCodes.JS5_RESPONSE_CONNECT_OUTOFDATE
            }

        val pipeline = ctx.pipeline()
        pipeline.apply {
            replace("packet.decoder", SimplePacketDecoder(updateCodec))
            replace("message.decoder", MessageDecoder(updateCodec))
            replace(
                "message.handler", NetworkMessageHandler(
                    updateCodec,
                    ServerNetworkEventHandler(UpdateSession(channel()))
                )
            )
            replace("message.encoder", MessageEncoder(updateCodec, PacketBuilder()))
        }
        pipeline.writeAndFlush(UpdateVersionMessage(response))
    }
}