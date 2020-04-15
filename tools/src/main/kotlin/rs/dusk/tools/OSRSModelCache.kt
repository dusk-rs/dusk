package rs.dusk.tools

import com.displee.cache.CacheLibrary

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 04, 2020
 */
object OSRSModelCache {
    @JvmStatic
    fun main(args: Array<String>) {
        val rs2 = CacheLibrary("C:\\Users\\Greg\\Documents\\hestia-bundle\\data\\cache\\")
        val osrs = CacheLibrary("C:\\Users\\Greg\\Downloads\\osrs-189\\cache\\")

        osrs.index(7).archiveIds().forEach { archive ->
            if (archive != 1362) {
                return@forEach
            }
            val data = osrs.data(7, archive, 0) ?: return@forEach
            rs2.put(7, archive, 0, data)
            println(archive)
        }

        rs2.update()
        println("Done")
    }
}