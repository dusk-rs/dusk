package rs.dusk.client.update.encode.player

import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.buffer.write.Writer
import rs.dusk.engine.entity.character.update.visual.player.MOVEMENT_TYPE_MASK
import rs.dusk.engine.entity.character.update.visual.player.MovementType
import rs.dusk.game.entity.character.update.VisualEncoder

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class MovementTypeEncoder : VisualEncoder<MovementType>(MOVEMENT_TYPE_MASK) {

    override fun encode(writer: Writer, visual: MovementType) {
        writer.writeByte(visual.type.id, Modifier.INVERSE)
    }

}