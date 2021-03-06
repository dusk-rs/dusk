package rs.dusk.engine.client.update.encode.player

import rs.dusk.core.io.Modifier
import rs.dusk.core.io.write.Writer
import rs.dusk.engine.entity.character.update.VisualEncoder
import rs.dusk.engine.entity.character.update.visual.player.MOVEMENT_TYPE_MASK
import rs.dusk.engine.entity.character.update.visual.player.MovementType

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class MovementTypeEncoder : VisualEncoder<MovementType>(MOVEMENT_TYPE_MASK) {

    override fun encode(writer: Writer, visual: MovementType) {
        writer.writeByte(visual.type.id, Modifier.INVERSE)
    }

}