package rs.dusk.game.container.player

import inject
import rs.dusk.cache.definition.entity.ItemDefinitionReader

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 25, 2020
 */
data class Item(val id : Int, val amount : Int) {
	
	val reader : ItemDefinitionReader by inject()
	
	val definitions = reader.get(id)
	
}