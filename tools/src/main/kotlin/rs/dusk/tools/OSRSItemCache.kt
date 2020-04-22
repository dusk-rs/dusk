package rs.dusk.tools

import io.netty.buffer.ByteBuf
import org.koin.core.context.startKoin
import org.koin.dsl.module
import rs.dusk.cache.Cache
import rs.dusk.cache.CacheDelegate

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 04, 2020
 */
object OSRSItemCache {
    @JvmStatic
    fun main(args: Array<String>) {
        val koin = startKoin { }.koin
        val rs2 = CacheDelegate("C:\\Users\\Greg\\Documents\\hestia-bundle\\data\\cache\\")

        @Suppress("USELESS_CAST")
        val rs2Module = module {
            single { rs2 as Cache }
        }

        @Suppress("USELESS_CAST")
        val osrsModule = module {
            single { CacheDelegate("C:\\Users\\Greg\\Downloads\\osrs-189\\cache\\") as Cache }
        }

        koin.loadModules(listOf(osrsModule))

        val osrsDecoder = OSRSItemDecoder()
        val osrsDefs = (0 until osrsDecoder.size).map { id -> id to osrsDecoder.get(id) }.toMap()

//        koin.unloadModules(listOf(osrsModule))
//        koin.loadModules(listOf(rs2Module))
//        val encoder = ObjectEncoder()
//        val rs2Decoder = ObjectDecoder(true, false)
        repeat(osrsDecoder.size) { id ->
//            val obj = rs2Decoder.get(id) ?: return@repeat
            val osrs = osrsDefs[id] ?: return@repeat

//            println(osrs)
//            obj.name = "${obj.name} $id"
//            println("${Arrays.deepToString(obj.modelIds)} ${Arrays.deepToString(osrs.modelIds)}")
//            println("${Arrays.toString(obj.modelTypes)} ${Arrays.toString(osrs.modelTypes)}")

//            println(id)
//            val writer = BufferWriter()
//            with(encoder) {
//                writer.encode(osrs)
//            }

//            val array = writer.buffer.toByteArray()
//            rs2.delegate.put(Indices.OBJECTS, rs2Decoder.getArchive(id), rs2Decoder.getFile(id), array)
        }
//        rs2.delegate.update()
    }

    fun ByteBuf.toByteArray(): ByteArray {
        val bytes = ByteArray(readableBytes())
        getBytes(readerIndex(), bytes)
        return bytes
    }
}