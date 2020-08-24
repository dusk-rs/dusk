import rs.dusk.engine.client.send
import rs.dusk.engine.event.then
import rs.dusk.engine.event.where
import rs.dusk.network.rs.codec.game.encode.message.ContainerItemsMessage
import rs.dusk.world.command.Command

Command where { prefix == "inter" } then {
    val id = content.toInt()
    if (id == -1) {
        val id = player.interfaces.get("main_screen") ?: return@then
        player.interfaces.close(id)
    } else {
        player.interfaces.open(id)
    }
}

Command where { prefix == "show" } then {
    val parts = content.split(" ")
    player.interfaces.sendVisibility(parts[0], parts[1], parts[2].toBoolean())
}

Command where { prefix == "sendItem" } then {
    val parts = content.split(" ")
    player.interfaces.sendItem(parts[0], parts[1], parts[2].toInt(), parts.getOrNull(3)?.toInt() ?: 1)
}

Command where { prefix == "setting" } then {
    val parts = content.split(" ")
    player.interfaces.sendSetting(parts[0], parts[1], parts[2].toInt(), parts[3].toInt(), parts.getOrNull(4)?.toInt() ?: 0)
}

Command where { prefix == "sendItems" } then {
    player.send(ContainerItemsMessage(90, IntArray(28) { 995 }, IntArray(28) { 1 }, true))
//    player.send(InterfaceItemsMessage(90, IntArray(28) { 995 }, IntArray(28) { 1 }, false))
//    player.send(InterfaceItemsMessage(90, IntArray(28) { 995 }, IntArray(28) { 1 }, true))
//    player.send(InterfaceItemsMessage(90, IntArray(28) { 995 }, IntArray(28) { 1 }, false))
}