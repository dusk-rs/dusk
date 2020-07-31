package rs.dusk.engine.entity.character.contain

import rs.dusk.cache.config.decoder.ItemContainerDecoder
import rs.dusk.engine.client.send
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.network.rs.codec.game.encode.message.InterfaceItemsMessage
import rs.dusk.utility.get

sealed class Containers(val id: Int) {
    object PriceChecker : Containers(90)
    object Inventory : Containers(93)
    object Equipment : Containers(94)
    object Bank : Containers(95)
    object DuelStake : Containers(134)
    object BeastOfBurden : Containers(530)

    sealed class Shop(id: Int, val title: String = "") : Containers(id) {
        object LletyaArcheryShop : Shop(303, "Lletya Archery Shop")
    }
}

fun Player.container(container: Containers): Container {
    return containers.getOrPut(container.id) {
        val instance = Container(
            decoder = get(),
            capacity = get<ItemContainerDecoder>().getSafe(container.id).length,
            stackMode = if (container == Containers.Bank) StackMode.Always else StackMode.Normal
        )
        instance.listeners.add { _, _, _ ->
            send(InterfaceItemsMessage(container.id, instance.items, instance.amounts))
        }
        instance
    }
}

val Player.inventory: Container
    get() = container(Containers.Inventory)

val Player.bank: Container
    get() = container(Containers.Bank)

val Player.equipment: Container
    get() = container(Containers.Equipment)

val Player.beastOfBurden: Container
    get() = container(Containers.BeastOfBurden)