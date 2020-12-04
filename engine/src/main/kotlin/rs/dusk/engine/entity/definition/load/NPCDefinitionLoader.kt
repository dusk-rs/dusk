package rs.dusk.engine.entity.definition.load

import rs.dusk.cache.definition.decoder.NPCDecoder
import rs.dusk.engine.TimedLoader
import rs.dusk.engine.entity.definition.NPCDefinitions
import rs.dusk.engine.io.file.FileIO

class NPCDefinitionLoader(private val IO: FileIO, private val decoder: NPCDecoder) :
    TimedLoader<NPCDefinitions>("npc definition") {

    override fun load(args: Array<out Any?>): NPCDefinitions {
        val path = args[0] as String
        val data: Map<String, Map<String, Any>> = IO.load(path)
        val names = data.map { it.value["id"] as Int to it.key }.toMap()
        count = names.size
        return NPCDefinitions(decoder, data, names)
    }
}