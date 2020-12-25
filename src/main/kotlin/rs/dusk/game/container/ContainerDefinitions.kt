package rs.dusk.game.container

import rs.dusk.cache.config.data.ContainerDefinition
import rs.dusk.cache.config.decoder.ContainerDecoder
import rs.dusk.cache.definition.entity.DefinitionsDecoder

class ContainerDefinitions(
	override val decoder : ContainerDecoder,
	override val extras : Map<String, Map<String, Any>>,
	override val names : Map<Int, String>
) : DefinitionsDecoder<ContainerDefinition, ContainerDecoder>