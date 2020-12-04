package rs.dusk.engine.entity.definition.load

import rs.dusk.cache.definition.decoder.ObjectDecoder
import rs.dusk.engine.TimedLoader
import rs.dusk.engine.entity.definition.ObjectDefinitions
import rs.dusk.engine.io.file.FileIO

class ObjectDefinitionLoader(private val decoder: ObjectDecoder, private val IO: FileIO) :
    TimedLoader<ObjectDefinitions>("object definition") {

    override fun load(args: Array<out Any?>): ObjectDefinitions {
        val path = args[0] as String
        val data: Map<String, Map<String, Any>> = IO.load(path)
        val names = data.map { it.value["id"] as Int to it.key }.toMap()
        count = names.size
        return ObjectDefinitions(decoder, data, names)
    }

}