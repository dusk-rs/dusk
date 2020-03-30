package org.redrune

import com.github.michaelbull.logging.InlineLogger
import com.google.common.base.Stopwatch
import org.koin.core.context.startKoin
import org.koin.logger.slf4jLogger
import org.redrune.cache.cacheModule
import org.redrune.core.network.codec.message.decode.OpcodeMessageDecoder
import org.redrune.core.network.codec.message.encode.RawMessageEncoder
import org.redrune.core.network.codec.message.handle.NetworkMessageHandler
import org.redrune.core.network.codec.packet.decode.SimplePacketDecoder
import org.redrune.core.network.connection.ConnectionPipeline
import org.redrune.core.network.connection.ConnectionSettings
import org.redrune.core.network.connection.server.NetworkServer
import org.redrune.core.tools.function.NetworkUtils
import org.redrune.core.tools.function.NetworkUtils.Companion.loadCodecs
import org.redrune.engine.script.ScriptLoader
import org.redrune.network.rs.codec.NetworkEventHandler
import org.redrune.network.rs.codec.game.GameCodec
import org.redrune.network.rs.codec.login.LoginCodec
import org.redrune.network.rs.codec.service.ServiceCodec
import org.redrune.network.rs.codec.update.UpdateCodec
import org.redrune.utility.getProperty
import org.redrune.world.World
import java.util.concurrent.TimeUnit.MILLISECONDS

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since 2020-01-07
 */
class GameServer(
    /**
     * The world this server represents
     */
    private val world: World
) {

    private val logger = InlineLogger()

    /**
     * The stopwatch instance
     */
    private val stopwatch = Stopwatch.createStarted()

    /**
     * If the game server is running
     */
    var running = false

    private fun bind() {
        val port = getProperty<Int>("port")!!
        val settings = ConnectionSettings("localhost", port + world.id)
        val server = NetworkServer(settings)
        val pipeline = ConnectionPipeline {
            it.addLast("packet.decoder", SimplePacketDecoder(ServiceCodec))
            it.addLast("message.decoder", OpcodeMessageDecoder(ServiceCodec))
            it.addLast("message.handler", NetworkMessageHandler(ServiceCodec, NetworkEventHandler()))
            it.addLast("message.encoder", RawMessageEncoder(ServiceCodec))
        }
        server.configure(pipeline)
        server.start()
    }

    /**
     * Tasks that need to be done before the server is loaded called here
     */
    private fun preload() {
        startKoin {
            slf4jLogger()
            modules(cacheModule)
            fileProperties("/game.properties")
            fileProperties("/rsa.properties")
        }
        ScriptLoader()

        val stopwatch = Stopwatch.createStarted()
        loadCodecs(ServiceCodec, UpdateCodec, LoginCodec, GameCodec)
        logger.info { "Took ${stopwatch.elapsed(MILLISECONDS)}ms to prepare all codecs" }
    }

    fun start() {
        preload()
        bind()

        logger.info {
            val name = getProperty<String>("name")
            val major = getProperty<Int>("buildMajor")
            val minor = getProperty<Float>("buildMinor")

            "$name v$major.$minor successfully booted world ${world.id} in ${stopwatch.elapsed(MILLISECONDS)} ms"
        }
        running = true
    }

}