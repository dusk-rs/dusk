package rs.dusk.engine.entity.definition.load

import rs.dusk.cache.config.decoder.ContainerDecoder
import rs.dusk.engine.TimedLoader
import rs.dusk.engine.entity.character.contain.StackMode
import rs.dusk.engine.entity.definition.ContainerDefinitions
import rs.dusk.engine.io.file.jackson.YAMLIO

class ContainerDefinitionLoader(private val IO: YAMLIO, private val decoder: ContainerDecoder) :
    TimedLoader<ContainerDefinitions>("container definition") {

    override fun load(args: Array<out Any?>): ContainerDefinitions {
        val path = args[0] as String
        val data: Map<String, Map<String, Any>> = IO.read(path)
        val map = data.mapValues { entry -> entry.value.mapValues { convert(it.key, it.value) } }.toMap()
        val names = data.map { it.value["id"] as Int to it.key }.toMap()
        count = names.size
        return ContainerDefinitions(decoder, map, names)
    }

    fun convert(key: String, value: Any): Any {
        return when (key) {
            "stack" -> StackMode.valueOf(value as String)
            else -> value
        }
    }
}