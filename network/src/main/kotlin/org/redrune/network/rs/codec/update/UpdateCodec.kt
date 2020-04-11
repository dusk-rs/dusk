package org.redrune.network.rs.codec.update

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.redrune.core.network.codec.buildCodec
import org.redrune.core.network.codec.config.DecoderConfig
import org.redrune.core.network.codec.config.EncoderConfig
import org.redrune.network.rs.codec.update.handle.UpdateConnectionMessageHandler
import org.redrune.network.rs.codec.update.handle.UpdateDisconnectionMessageHandler
import org.redrune.network.rs.codec.update.handle.UpdateLoginStatusHandler
import org.redrune.network.rs.codec.update.handle.UpdateRequestMessageHandler

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
val updateCodecModule = module {
    single(named("updateCodec"), createdAtStart = true) {
        buildCodec {
            setDecoders(DecoderConfig.load("./data/codec/update-decoders.yml"))
            setEncoders(EncoderConfig.load("./data/codec/update-encoders.yml"))
            bind(UpdateConnectionMessageHandler())
            bind(UpdateDisconnectionMessageHandler())
            bind(UpdateLoginStatusHandler())
            bind(UpdateRequestMessageHandler())
        }
    }
}