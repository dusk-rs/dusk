package rs.dusk.tools.definition.item.pipe.extra.wiki

import rs.dusk.tools.Pipeline
import rs.dusk.tools.definition.item.Extras
import rs.dusk.tools.definition.item.pipe.extra.wiki.InfoBoxItem.Companion.removeLinks
import rs.dusk.tools.wiki.model.WikiPage

class InfoBoxMonster : Pipeline.Modifier<Extras> {
    override fun modify(content: Extras): Extras {
        val (builder, extras) = content
        val (_, _, page, _, _) = builder
        process(extras, page)
        return content
    }

    private fun process(extras: MutableMap<String, Any>, page: WikiPage?) {
        val template = page?.getTemplateMap("infobox monster") ?: return
        template.forEach { (key, value) ->
            when (key) {
                "examine" -> {
                    val text = removeLinks(value as String)
                    InfoBoxItem.splitExamine(text, extras, key, "", false)
                }
                else -> return@forEach
            }
        }
    }

}