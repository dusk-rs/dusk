package rs.dusk.client.update.encode.player

import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.buffer.write.Writer
import rs.dusk.engine.entity.character.update.visual.player.MINIMAP_HIGHLIGHT_MASK
import rs.dusk.engine.entity.character.update.visual.player.MinimapHighlight
import rs.dusk.game.entity.character.update.VisualEncoder

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class MinimapHighlightEncoder : VisualEncoder<MinimapHighlight>(MINIMAP_HIGHLIGHT_MASK) {

    override fun encode(writer: Writer, visual: MinimapHighlight) {
        writer.writeByte(visual.highlighted, Modifier.SUBTRACT)
    }

}