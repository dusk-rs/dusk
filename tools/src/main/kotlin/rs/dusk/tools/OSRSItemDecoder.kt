package rs.dusk.tools

import rs.dusk.cache.config.ConfigDecoder
import rs.dusk.cache.definition.data.ItemDefinition
import rs.dusk.core.io.read.Reader

/**
 * #181
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 13, 2020
 */
class OSRSItemDecoder : ConfigDecoder<ItemDefinition>(10) {

    override fun create() = ItemDefinition()

    override fun ItemDefinition.read(opcode: Int, buffer: Reader) {
        when (opcode) {
            1 -> modelId = buffer.readUnsignedShort()
            2 -> name = buffer.readString()
            4 -> spriteScale = buffer.readUnsignedShort()
            5 -> spritePitch = buffer.readUnsignedShort()
            6 -> spriteCameraRoll = buffer.readUnsignedShort()
            7 -> {
                spriteTranslateX = buffer.readUnsignedShort()
                if (spriteTranslateX > 32767) {
                    spriteTranslateX -= 65536
                }
            }
            8 -> {
                spriteTranslateY = buffer.readUnsignedShort()
                if (spriteTranslateY > 32767) {
                    spriteTranslateY -= 65536
                }
            }
            11 -> stackable = 1
            12 -> {
                val field3428 = buffer.readInt()
            }
            16 -> members = true
            23 -> {
                primaryMaleModel = buffer.readUnsignedShort()
                maleWieldY = buffer.readUnsignedByte()
            }
            24 -> secondaryMaleModel = buffer.readUnsignedShort()
            25 -> {
                primaryFemaleModel = buffer.readUnsignedShort()
                femaleWieldY = buffer.readUnsignedByte()
            }
            26 -> secondaryFemaleModel = buffer.readUnsignedShort()
            in 30..34 -> {
                this.floorOptions[opcode - 30] = buffer.readString()
                if (floorOptions[opcode - 30].equals("Hidden", ignoreCase = true)) {
                    floorOptions[opcode - 30] = null
                }
            }
            in 35..39 -> {
                options[opcode - 35] = buffer.readString()
            }
            40 -> readColours(buffer)
            41 -> readTextures(buffer)
            42 -> {
                val shiftClickDropIndex = buffer.readByte()
            }
            65 -> {
                val tradable = true
            }
            78 -> tertiaryMaleModel = buffer.readUnsignedShort()
            79 -> tertiaryFemaleModel = buffer.readUnsignedShort()
            90 -> primaryMaleDialogueHead = buffer.readUnsignedShort()
            91 -> primaryFemaleDialogueHead = buffer.readUnsignedShort()
            92 -> secondaryMaleDialogueHead = buffer.readUnsignedShort()
            93 -> secondaryFemaleDialogueHead = buffer.readUnsignedShort()
            95 -> spriteCameraYaw = buffer.readUnsignedShort()
            97 -> noteId = buffer.readUnsignedShort()
            98 -> notedTemplateId = buffer.readUnsignedShort()
            in 100..109 -> {
                if (stackIds == null) {
                    stackIds = IntArray(10)
                    stackAmounts = IntArray(10)
                }
                stackIds!![opcode - 100] = buffer.readUnsignedShort()
                stackAmounts!![opcode - 100] = buffer.readUnsignedShort()
            }
            110 -> floorScaleX = buffer.readUnsignedShort()
            111 -> floorScaleY = buffer.readUnsignedShort()
            112 -> floorScaleZ = buffer.readUnsignedShort()
            113 -> ambience = buffer.readByte()
            114 -> diffusion = buffer.readByte()
            115 -> team = buffer.readUnsignedByte()
            139 -> {
                val boughtLink = buffer.readUnsignedShort()
            }
            140 -> {
                val boughtTemplate = buffer.readUnsignedShort()
            }
            148 -> {
                val placeholderId = buffer.readUnsignedShort()
            }
            149 -> {
                val placeholderTemplateId = buffer.readUnsignedShort()
            }
            249 -> readParameters(buffer)
        }
    }

    override fun ItemDefinition.changeValues() {
    }
}