package rs.dusk.cache.definition.entity

import rs.dusk.cache.definition.data.ItemDefinition
import rs.dusk.cache.definition.decoder.ItemDecoder

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 25, 2020
 */
class ItemDefinitionReader(
	override val decoder : ItemDecoder,
	override val extras : Map<String, Map<String, Any>>,
	override val names : Map<Int, String>
) : DefinitionsDecoder<ItemDefinition, ItemDecoder>