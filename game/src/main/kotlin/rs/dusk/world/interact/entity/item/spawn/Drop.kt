package rs.dusk.world.interact.entity.item.spawn

import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.entity.definition.ItemDefinitions
import rs.dusk.engine.entity.item.FloorItem
import rs.dusk.engine.event.Event
import rs.dusk.engine.event.EventCompanion
import rs.dusk.engine.map.Tile
import rs.dusk.utility.get

/**
 * Spawns a floor item
 * Note: Not concerned with where the item is coming from
 * @param id The id of the item to spawn
 * @param amount The stack size of the item to spawn
 * @param tile The tile on which to spawn the item
 * @param revealTicks Number of ticks before the item is revealed to all
 * @param disappearTicks Number of ticks before the item is removed
 * @param owner The index of the owner of the item
 */
data class Drop(
    val id: Int,
    val amount: Int,
    val tile: Tile,
    val revealTicks: Int = -1,
    val disappearTicks: Int = -1,
    val owner: Player? = null
) : Event<FloorItem>() {

    constructor(
        name: String,
        amount: Int,
        tile: Tile,
        revealTicks: Int = -1,
        disappearTicks: Int = -1,
        owner: Player? = null
    ) : this(get<ItemDefinitions>().getId(name), amount, tile, revealTicks, disappearTicks, owner)

    companion object : EventCompanion<Drop>
}