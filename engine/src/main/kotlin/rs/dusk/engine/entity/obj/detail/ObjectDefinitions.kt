package rs.dusk.engine.entity.obj.detail

import rs.dusk.cache.definition.data.ObjectDefinition
import rs.dusk.cache.definition.decoder.ObjectDecoder
import rs.dusk.engine.entity.DefinitionsDecoder

class ObjectDefinitions(
    override val decoder: ObjectDecoder,
    override val extras: Map<String, Map<String, Any>>,
    override val names: Map<Int, String>
) : DefinitionsDecoder<ObjectDefinition, ObjectDecoder>