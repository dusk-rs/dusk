package rs.dusk.world.interact.dialogue

import rs.dusk.cache.definition.decoder.ItemDecoder
import rs.dusk.engine.client.ui.dialogue.DialogueContext
import rs.dusk.engine.client.ui.open
import rs.dusk.engine.client.variable.getVar
import rs.dusk.engine.client.variable.setVar
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.utility.get


private const val INTERFACE_NAME = "skill_creation"
private const val INTERFACE_AMOUNT_NAME = "skill_creation_amount"
private const val DEFAULT_TEXT = "Choose how many you wish to make, then<br>click on the chosen item to begin."

suspend fun DialogueContext.makeAmount(items: List<Int>, type: String, maximum: Int, text: String = DEFAULT_TEXT): Pair<Int, Int> {
    return if(player.open(INTERFACE_NAME) && player.open(INTERFACE_AMOUNT_NAME)) {
        if (type != "Make sets" && type != "Make2" && type != "Make sets2") {
            player.interfaces.sendSettings(INTERFACE_AMOUNT_NAME, "all", -1, 0, 0)
        }
        player.interfaces.sendVisibility(INTERFACE_NAME, "custom", false)
        player.interfaces.sendText(INTERFACE_AMOUNT_NAME, "line1", text)
        player.setVar("skill_creation_type", type)

        setItemOptions(player, items)
        setMax(player, maximum)
        val choice: Int = await("make")
        val id = items.getOrNull(choice) ?: -1
        val amount = player.getVar("skill_creation_amount", 0)
        id to amount
    } else {
        -1 to 0
    }
}

private fun setItemOptions(player: Player, items: List<Int>) {
    val decoder: ItemDecoder = get()
    repeat(10) { index ->
        val item = items.getOrNull(index) ?: -1
        player.setVar("skill_creation_item_$index", item)
        if (item != -1) {
            player.setVar("skill_creation_name_$index", decoder.getSafe(item).name)
        }
    }
}

private fun setMax(player: Player, maximum: Int) {
    player.setVar("skill_creation_maximum", maximum)
    val amount = player.getVar("skill_creation_amount", maximum)
    if (amount > maximum) {
        player.setVar("skill_creation_amount", maximum)
    }
}