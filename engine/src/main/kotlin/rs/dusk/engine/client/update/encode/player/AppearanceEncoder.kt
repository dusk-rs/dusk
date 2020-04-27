package rs.dusk.engine.client.update.encode.player

import rs.dusk.cache.secure.Encryption.encryptMD5
import rs.dusk.core.io.Modifier
import rs.dusk.core.io.write.BufferWriter
import rs.dusk.core.io.write.Writer
import rs.dusk.engine.entity.model.visual.VisualEncoder
import rs.dusk.engine.entity.model.visual.visuals.player.APPEARANCE_MASK
import rs.dusk.engine.entity.model.visual.visuals.player.Appearance

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class AppearanceEncoder : VisualEncoder<Appearance>(APPEARANCE_MASK) {

    override fun encode(writer: Writer, visual: Appearance) {
        val buffer = BufferWriter()
        val (male,
            skillLevel,
            size,
            trimTitle,
            title,
            prefix,
            skull,
            headIcon,
            hidden,
            transform,
            look,
            colours,
            equipment,
            emote,
            displayName,
            combatLevel,
            summoningCombatLevel) = visual
        buffer.apply {
            var flag = 0
            flag = flag or 0x1// Gender
            flag = flag or 0x2// Display name
            if (skillLevel != -1) {
                flag = flag or 0x4// Display skill level rather than combat
            }
            flag = flag or (size shl 3 and 0x7)
//            flag = flag and ((1 and 0xf2) shr 6)// Something about trimming title
            writeByte(flag)
            writeByte(title)
            writeString(prefix)
            writeByte(skull)
            writeByte(headIcon)
            writeByte(hidden)

            if (transform != -1) {
                writeShort(-1)
                writeShort(transform)
                writeByte(0)
            } else {
                for (item in 0 until 13) {
                    writeEmpty()
                }
                writeShort(0)
            }
            colours.forEach { colour ->
                writeByte(colour)
            }
            writeShort(emote)
            writeString(displayName)
            writeByte(combatLevel)
            if (skillLevel != -1) {
                writeShort(-1)// Skill level
            } else {
                writeByte(summoningCombatLevel)// Combat level + summoning
                writeByte(-1)// Skill level
            }

            writeByte(transform != -1)
            if (transform != -1) {
                writeShort(-1)
                writeShort(-1)
                writeShort(-1)
                writeShort(0)
                writeByte(0)
            }
        }

        val encrypted = encryptMD5(buffer.toArray()) ?: return
        writer.writeByte(encrypted.size, Modifier.SUBTRACT)
        writer.writeBytes(encrypted)
    }

    private fun Writer.writeEmpty() = writeByte(0)

    private fun Writer.writeItem(item: Int) = writeShort(item or 0x8000)

    private fun Writer.writeClothes(value: Int) = writeShort(value or 0x100)

}