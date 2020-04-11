package org.redrune.network

import com.github.michaelbull.logging.InlineLogger
import com.google.common.base.Stopwatch
import java.util.concurrent.TimeUnit

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since March 31, 2020
 */
class NetworkRegistry {

    private val logger = InlineLogger()

    fun register() {
//        NetworkUtils.loadCodecs(ServiceCodec, UpdateCodec, LoginCodec, GameCodec)
        val stopwatch = Stopwatch.createStarted()
//        NetworkUtils.loadCodecs(ServiceCodec, UpdateCodec, LoginCodec, GameCodec)
        logger.info { "Took ${stopwatch.elapsed(TimeUnit.MILLISECONDS)}ms to prepare all codecs" }
    }
}