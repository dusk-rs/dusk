package org.redrune.network.rs.codec.login

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.redrune.core.network.codec.buildCodec
import org.redrune.core.network.codec.config.DecoderConfig
import org.redrune.core.network.codec.config.EncoderConfig
import org.redrune.network.rs.codec.login.handle.LobbyLoginMessageHandler

/**
 * @author Tyluur <contact@kiaira.tech>
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since February 18, 2020
 */
val loginCodecModule = module {
    single(named("loginCodec"), createdAtStart = true) {
        buildCodec {
            setDecoders(DecoderConfig.load("./data/codec/login-decoders.yml"))
            setEncoders(EncoderConfig.load("./data/codec/login-encoders.yml"))
            bind(LobbyLoginMessageHandler())
        }
    }
}