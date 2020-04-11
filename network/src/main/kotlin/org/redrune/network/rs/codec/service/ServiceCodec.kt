package org.redrune.network.rs.codec.service

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.redrune.core.network.codec.buildCodec
import org.redrune.core.network.codec.config.DecoderConfig
import org.redrune.network.rs.codec.service.handle.GameConnectionHandshakeMessageHandler
import org.redrune.network.rs.codec.service.handle.UpdateHandshakeMessageHandler

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
val serviceCodecModule = module {
    single(named("serviceCodec"), createdAtStart = true) {
        buildCodec {
            setDecoders(DecoderConfig.load("./data/codec/service-decoders.yml"))
            bind(GameConnectionHandshakeMessageHandler())
            bind(UpdateHandshakeMessageHandler())
        }
    }
}