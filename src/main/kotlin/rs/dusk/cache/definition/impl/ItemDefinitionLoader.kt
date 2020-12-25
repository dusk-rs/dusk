package rs.dusk.cache.definition.impl

import rs.dusk.cache.definition.decoder.ItemDecoder
import rs.dusk.cache.definition.entity.ItemDefinitionReader
import rs.dusk.cache.definition.entity.item.EquipSlot
import rs.dusk.cache.definition.entity.item.EquipType
import rs.dusk.cache.definition.entity.item.ItemKept
import rs.dusk.core.boot.TimedLoader
import rs.dusk.utility.io.file.FileLoader

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 25, 2020
 */
class ItemDefinitionLoader(private val decoder : ItemDecoder) : TimedLoader<ItemDefinitionReader>("item definition") {
	private val loader = FileLoader()
	private var equipmentCount = 0
	private val equipmentIndices = (0 until decoder.size).map {
		val def = decoder.getOrNull(it)
		it to if (def != null && (def.primaryMaleModel >= 0 || def.primaryFemaleModel >= 0)) {
			equipmentCount++
		} else {
			-1
		}
	}.toMap()
	
	override fun load(args : Array<out Any?>) : ItemDefinitionReader {
		val path = args[0] as String
		val data : Map<String, Map<String, Any>> = loader.load(path)!!
		val map = data.mapValues { entry ->
			entry.value.mapValues { convert(it.key, it.value) }.toMutableMap().apply {
				this["equip"] = equipmentIndices.getOrDefault(entry.value["id"] as Int, -1)
			}
		}.toMap()
		val names = data.map { it.value["id"] as Int to it.key }.toMap()
		count = names.size
		return ItemDefinitionReader(decoder, map, names)
	}
	
	fun convert(key : String, value : Any) : Any {
		return when (key) {
			"slot" -> EquipSlot.valueOf(value as String)
			"type" -> EquipType.valueOf(value as String)
			"kept" -> ItemKept.valueOf(value as String)
			else   -> value
		}
	}
	
}