package rs.dusk

import com.github.michaelbull.logging.InlineLogger
import com.google.common.base.Stopwatch
import org.koin.core.context.startKoin
import org.koin.logger.slf4jLogger
import rs.dusk.cache.cacheConfigModule
import rs.dusk.cache.cacheDefinitionModule
import rs.dusk.cache.cacheModule
import rs.dusk.cache.definition.entity.detailsModule
import rs.dusk.core.gameWorkerModule
import rs.dusk.core.path.pathFindModule
import rs.dusk.core.render.renderSequenceModule
import rs.dusk.engine.client.clientSessionModule
import rs.dusk.engine.event.eventModule
import rs.dusk.game.entity.registry.playerRegistryModule
import rs.dusk.game.world.map.decrypt.xteaLoaderModule
import rs.dusk.game.world.worldModule
import rs.dusk.network.codec.download.downloadCodecModule
import rs.dusk.network.codec.game.gameCodecModule
import rs.dusk.network.codec.handshake.handshakeCodecModule
import rs.dusk.network.codec.login.loginCodecModule
import rs.dusk.network.gameServerModule
import rs.dusk.utility.io.file.fileLoaderModule
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
			
			// properties
			fileProperties("/game.properties")
			
			// network codec modules
			modules(gameServerModule, handshakeCodecModule, downloadCodecModule, loginCodecModule, gameCodecModule)
			
			// cache modules
			modules(cacheModule, xteaLoaderModule, cacheDefinitionModule, cacheConfigModule, detailsModule)
			
			// registry
			modules(playerRegistryModule)
			
			// core
			modules(gameWorkerModule, renderSequenceModule)
			
			// io
			modules(fileLoaderModule)
			
			// world
			modules(worldModule)
			
			// events
			modules(eventModule)
			
			// PF
			modules(pathFindModule)
			
			// sessions
			modules(clientSessionModule)
		}
		logger.info { "Dusk is live (${stopwatch.elapsed(MILLISECONDS)} ms)" }
	}
	
	companion object {
		private val logger = InlineLogger()
	}
}