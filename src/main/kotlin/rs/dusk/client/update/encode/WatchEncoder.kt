package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.buffer.write.Writer
import rs.dusk.engine.entity.character.update.visual.Watch
import rs.dusk.game.entity.character.update.VisualEncoder

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class WatchEncoder(private val npc: Boolean, mask: Int) : VisualEncoder<Watch>(mask) {

    override fun encode(writer: Writer, visual: Watch) {
        writer.writeShort(
            visual.index,
            if (npc) Modifier.NONE else Modifier.ADD,
            if (npc) Endian.LITTLE else Endian.BIG
        )
    }

}