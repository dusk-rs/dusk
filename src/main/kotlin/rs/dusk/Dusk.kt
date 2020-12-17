package rs.dusk

import com.github.michaelbull.logging.InlineLogger
import org.koin.core.context.startKoin
import org.koin.logger.slf4jLogger
import rs.dusk.network.codec.download.downloadCodecModule
import rs.dusk.network.codec.handshake.handshakeCodecModule
import rs.dusk.network.gameServerModule

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @Since December 17, 2020
 */
class Dusk {
	
	private val logger = InlineLogger()
	
	fun start() {
		startKoin {
			slf4jLogger()
			// network codecs
			modules(gameServerModule, handshakeCodecModule, downloadCodecModule)
		}
		logger.info { "Dusk is live!" }
	}
	
}