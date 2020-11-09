package rs.dusk.tools.definition.item.pipe.extra.wiki

import rs.dusk.tools.Pipeline
import rs.dusk.tools.definition.item.ItemExtras
import rs.dusk.tools.definition.item.pipe.extra.wiki.InfoBoxItem.Companion.removeLinks
import rs.dusk.tools.wiki.model.WikiPage

class InfoBoxPet : Pipeline.Modifier<ItemExtras> {
    private val regex = "([0-9]+)\\s?\\[\\[(.*?)]]".toRegex()

    override fun modify(content: ItemExtras): ItemExtras {
        val (builder, extras) = content
        val (id, _, page, _, rs3, _) = builder
        process(extras, rs3, id)
        process(extras, page, id)
        return content
    }

    private fun process(extras: MutableMap<String, Any>, page: WikiPage?, id: Int) {
        val templates = page?.getTemplateMaps("infobox pet")
        val template = templates?.firstOrNull { (it["itemid"] as? String)?.toIntOrNull() == id } ?: page?.getTemplateMap("infobox pet") ?: return
        template.forEach { (key, value) ->
            when (key) {
                "growthtime" -> {
                    extras.putIfAbsent(key, (value as String).toIntOrNull() ?: 0)
                }
                "skillrequired" -> {
                    val text = value as String
                    if (text.contains("[[")) {
                        val map = regex.findAll(text).map {
                            it.groupValues[2].toLowerCase() to it.groupValues[1].toInt()
                        }.toMap()
                        if (map.isNotEmpty()) {
                            extras.putIfAbsent("skill_req", map)
                        }
                    }
                }
                "npcid" -> {
                    // TODO replace with npc name
                    val text = value as String
                    if (text.contains(",")) {
                        text.split(",").forEachIndexed { index, id ->
                            extras.putIfAbsent("npc${index + 1}", id.trim().toInt())
                        }
                    } else {
                        extras["npc"] = text.trim().toInt()
                    }
                }
                "food" -> {
                    // TODO replace with item name
                    InfoBoxItem.splitByLineBreak(value as String, extras, key, "", false)
                }
                "examine" -> {
                    val examine = removeLinks(value as String)
                    InfoBoxItem.splitByLineBreak(examine, extras, key, "", false)
                }
                else -> return@forEach
            }
        }
    }

}