package rs.dusk

import com.github.michaelbull.logging.InlineLogger
import org.koin.core.context.startKoin
import org.koin.logger.slf4jLogger
import rs.dusk.cache.cacheModule
import rs.dusk.network.codec.download.downloadCodecModule
import rs.dusk.network.codec.game.gameCodecModule
import rs.dusk.network.codec.handshake.handshakeCodecModule
import rs.dusk.network.codec.login.loginCodecModule
import rs.dusk.network.gameServerModule
import java.io.File

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class Dusk {
	
	fun start() {
		val file = File("game.properties")
		println(file.absolutePath)
		startKoin {
			slf4jLogger()
			// network codecs
			modules(gameServerModule, handshakeCodecModule, downloadCodecModule, loginCodecModule, gameCodecModule)
			// cache module
			modules(cacheModule)
			fileProperties("/game.properties")
		}
		logger.info { "Dusk is live!" }
	}
	
	companion object {
		private val logger = InlineLogger()
	}
}