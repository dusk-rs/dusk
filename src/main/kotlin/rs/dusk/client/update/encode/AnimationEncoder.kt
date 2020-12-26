package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.buffer.write.Writer
import rs.dusk.game.entity.character.update.visual.Animation
import rs.dusk.game.entity.character.update.VisualEncoder

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class AnimationEncoder(private val npc : Boolean, mask : Int) : VisualEncoder<Animation>(mask) {
	
	override fun encode(writer : Writer, visual : Animation) {
		val (first, second, third, fourth, speed) = visual
		writer.apply {
			val order = if (npc) Endian.BIG else Endian.LITTLE
			writeShort(first, order = order)
			writeShort(second, order = order)
			writeShort(third, order = order)
			writeShort(fourth, order = order)
			writeByte(speed, if (npc) Modifier.NONE else Modifier.ADD)
		}
	}
}