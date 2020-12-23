package rs.dusk

import com.github.michaelbull.logging.InlineLogger
import com.google.common.base.Stopwatch
import org.koin.core.context.startKoin
import org.koin.logger.slf4jLogger
import rs.dusk.cache.cacheModule
import rs.dusk.core.gameWorkerModule
import rs.dusk.core.render.renderSequenceModule
import rs.dusk.game.entity.registry.playerRegistryModule
import rs.dusk.game.world.map.decrypt.xteaLoaderModule
import rs.dusk.network.codec.download.downloadCodecModule
import rs.dusk.network.codec.game.gameCodecModule
import rs.dusk.network.codec.handshake.handshakeCodecModule
import rs.dusk.network.codec.login.loginCodecModule
import rs.dusk.network.gameServerModule
import java.util.concurrent.TimeUnit.MILLISECONDS

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class Dusk {
	
	fun start() {
		val stopwatch = Stopwatch.createStarted()
		startKoin {
			slf4jLogger()
			
			// network codec modules
			modules(gameServerModule, handshakeCodecModule, downloadCodecModule, loginCodecModule, gameCodecModule)
			// cache modules
			modules(cacheModule, xteaLoaderModule)
			// registry
			modules(playerRegistryModule)
			// core
			modules(gameWorkerModule, renderSequenceModule)
			// properties
			fileProperties("/game.properties")
		}
		logger.info { "Dusk is live (${stopwatch.elapsed(MILLISECONDS)} ms)" }
	}
	
	companion object {
		private val logger = InlineLogger()
	}
}