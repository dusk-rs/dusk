package rs.dusk.cache.definition.entity.anim

import rs.dusk.cache.definition.data.AnimationDefinition
import rs.dusk.cache.definition.decoder.AnimationDecoder
import rs.dusk.cache.definition.entity.DefinitionsDecoder

class AnimationDefinitions(
    override val decoder: AnimationDecoder,
    override val extras: Map<String, Map<String, Any>>,
    override val names: Map<Int, String>
) : DefinitionsDecoder<AnimationDefinition, AnimationDecoder>