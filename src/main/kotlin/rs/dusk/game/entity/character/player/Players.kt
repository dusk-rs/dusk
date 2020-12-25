package rs.dusk.game.entity.character.player

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
import rs.dusk.game.entity.PooledMapList
import rs.dusk.game.entity.character.MAX_PLAYERS
import java.util.*

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 25, 2020
 */
@Suppress("ArrayInDataClass")
data class Players(
	override val data : Int2ObjectOpenHashMap<ObjectLinkedOpenHashSet<Player?>> = Int2ObjectOpenHashMap(
		MAX_PLAYERS
	),
	override val pool : LinkedList<ObjectLinkedOpenHashSet<Player?>> = LinkedList(),
	override val indexed : Array<Player?> = arrayOfNulls(MAX_PLAYERS)
) : PooledMapList<Player>