package rs.dusk

import com.github.michaelbull.logging.InlineLogger
import org.koin.core.context.startKoin
import org.koin.logger.slf4jLogger
import rs.dusk.network.networkModule

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @Since December 17, 2020
 */
class Dusk {
	
	private val logger = InlineLogger()
	
	fun start() {
		startKoin {
			slf4jLogger()
			modules(networkModule)
			fileProperties("./data/game.properties")
		}
		logger.info { "Dusk is live!" }
	}
	
}