package rs.dusk.tools.detail

import org.koin.core.context.startKoin
import rs.dusk.cache.definition.decoder.ObjectDecoder
import rs.dusk.engine.client.cacheDefinitionModule
import rs.dusk.engine.client.cacheModule
import rs.dusk.engine.io.file.FileIO

/**
 * Dumps unique string identifiers for objects using formatted object definition name plus index for duplicates
 */
private class ObjectNames(val decoder: ObjectDecoder) : NameDumper() {


    override fun createName(id: Int): String? {
        return decoder.getOrNull(id)?.name
    }

    override fun createData(id: Int): Map<String, Any> {
        return mutableMapOf("id" to id)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val koin = startKoin {
                fileProperties("/tool.properties")
                modules(cacheModule, cacheDefinitionModule)
            }.koin
            val decoder = ObjectDecoder(koin.get(), member = true, lowDetail = false)
            val loader: FileIO = koin.get()
            val names = ObjectNames(decoder)
            names.dump(loader, "./object-details.yml", "object", decoder.size)
        }
    }

}