package rs.dusk.core.boot

import com.github.michaelbull.logging.InlineLogger
import org.koin.core.time.measureDurationForResult
import rs.dusk.utility.func.plural

/**
 * @since December 25, 2020
 */
abstract class TimedLoader<T>(private val name: String) {
	
	var count = 0
	
	fun run(vararg args: Any?): T {
		val (result, time) = measureDurationForResult {
			load(args)
		}
		logger.info { "Loaded $count ${name.plural(count)} in ${time.toInt()}ms" }
		return result
	}
	
	abstract fun load(args: Array<out Any?>): T
	
	companion object {
		private val logger = InlineLogger()
	}
}