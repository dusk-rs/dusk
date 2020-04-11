package org.redrune.network.rs.codec.game

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.redrune.core.network.codec.buildCodec
import org.redrune.core.network.codec.config.DecoderConfig
import org.redrune.core.network.codec.config.EncoderConfig
import org.redrune.network.rs.codec.game.handle.WorldListRefreshMessageHandler

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
val gameCodecModule = module {
    single(named("gameCodec"), createdAtStart = true) {
        buildCodec {
            setDecoders(DecoderConfig.load("./data/codec/game-decoders.yml"))
            setEncoders(EncoderConfig.load("./data/codec/game-encoders.yml"))
            bind(WorldListRefreshMessageHandler())
        }
    }
}