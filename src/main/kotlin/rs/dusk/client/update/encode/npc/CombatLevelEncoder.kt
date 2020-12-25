package rs.dusk.client.update.encode.npc

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.write.Writer
import rs.dusk.engine.entity.character.update.visual.npc.COMBAT_LEVEL_MASK
import rs.dusk.engine.entity.character.update.visual.npc.CombatLevel
import rs.dusk.game.entity.character.update.VisualEncoder

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class CombatLevelEncoder : VisualEncoder<CombatLevel>(COMBAT_LEVEL_MASK) {

    override fun encode(writer: Writer, visual: CombatLevel) {
        writer.writeShort(visual.level, order = Endian.LITTLE)
    }

}