package rs.dusk.tools

import com.displee.cache.CacheLibrary
import org.koin.core.context.startKoin
import org.koin.dsl.module
import rs.dusk.cache.CacheDelegate

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 04, 2020
 */
object OSRSObjectCache {
    @JvmStatic
    fun main(args: Array<String>) {
        val rs2 = CacheLibrary("C:\\Users\\Greg\\Documents\\hestia-bundle\\data\\cache\\")
        startKoin {
            modules(module {
                single { CacheDelegate("C:\\Users\\Greg\\Downloads\\osrs-189\\cache\\", "0", "1") }
            })
        }
        val decoder = OSRSObjectDecoder(false)
        val obj = decoder.get(1)
        println(obj)
    }
}