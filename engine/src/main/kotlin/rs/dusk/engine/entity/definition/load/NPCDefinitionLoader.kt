package rs.dusk.engine.entity.definition.load

import rs.dusk.cache.definition.decoder.NPCDecoder
import rs.dusk.engine.TimedLoader
import rs.dusk.engine.entity.definition.NPCDefinitions
import rs.dusk.engine.io.jackson.JacksonIO

class NPCDefinitionLoader(private val io: JacksonIO, private val decoder: NPCDecoder) :
    TimedLoader<NPCDefinitions>("npc definition") {

    override fun load(args: Array<out Any?>): NPCDefinitions {
        val path = args[0] as String
        val data: Map<String, Map<String, Any>> = io.load(path)
        val names = data.map { it.value["id"] as Int to it.key }.toMap()
        count = names.size
        return NPCDefinitions(decoder, data, names)
    }
}