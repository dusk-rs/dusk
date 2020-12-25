package rs.dusk.client.update.encode.npc

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.write.Writer
import rs.dusk.engine.entity.character.update.visual.npc.COMBAT_LEVEL_MASK
import rs.dusk.engine.entity.character.update.visual.npc.CombatLevel
import rs.dusk.game.entity.character.update.VisualEncoder
import rs.dusk.game.entity.character.update.visual.npc.TRANSFORM_MASK
import rs.dusk.game.entity.character.update.visual.npc.Transformation


/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class TransformEncoder : VisualEncoder<Transformation>(TRANSFORM_MASK) {
	
	override fun encode(writer : Writer, visual : Transformation) {
		writer.writeShort(visual.id, order = Endian.LITTLE)
	}
	
}