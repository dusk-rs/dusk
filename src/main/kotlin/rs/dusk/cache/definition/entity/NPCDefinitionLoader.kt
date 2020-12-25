package rs.dusk.cache.definition.entity

import rs.dusk.cache.definition.decoder.NPCDecoder
import rs.dusk.cache.definition.entity.npc.NPCDefinitions
import rs.dusk.core.boot.TimedLoader
import rs.dusk.utility.io.file.FileLoader

class NPCDefinitionLoader(private val loader : FileLoader, private val decoder : NPCDecoder) :
	TimedLoader<NPCDefinitions>("npc definition") {
	
	override fun load(args : Array<out Any?>) : NPCDefinitions {
		val path = args[0] as String
		val data : Map<String, Map<String, Any>> = loader.load(path)!!
		val names = data.map { it.value["id"] as Int to it.key }.toMap()
		count = names.size
		return NPCDefinitions(decoder, data, names)
	}
}