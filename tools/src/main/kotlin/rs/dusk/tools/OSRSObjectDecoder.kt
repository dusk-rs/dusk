package rs.dusk.tools

import rs.dusk.cache.config.ConfigDecoder
import rs.dusk.cache.definition.data.ObjectDefinition
import rs.dusk.core.io.read.Reader

/**
 * #181
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 13, 2020
 */
class OSRSObjectDecoder(val lowDetail: Boolean) : ConfigDecoder<ObjectDefinition>(6) {

    override fun create() = ObjectDefinition()

    override fun ObjectDefinition.read(opcode: Int, buffer: Reader) {
        when (opcode) {
            1 -> {
                val length = buffer.readUnsignedByte()
                if (length > 0) {
                    if (modelIds != null && !lowDetail) {
                        buffer.buffer.position(buffer.buffer.position() + length * 3)
                    } else {
                        val modelIds = arrayOfNulls<IntArray>(1)
                        modelIds[0] = IntArray(length)
                        modelTypes = ByteArray(length)
                        repeat(length) { index ->
                            modelIds[0]!![index] = buffer.readUnsignedShort()
                            modelTypes!![index] = buffer.readUnsignedByte().toByte()
                        }
                        this.modelIds = modelIds.filterNotNull().toTypedArray()
                    }
                }
            }
            2 -> name = buffer.readString()
            5 -> {
                val length = buffer.readUnsignedByte()
                if (length > 0) {
                    if (modelIds != null && !lowDetail) {
                        buffer.buffer.position(buffer.buffer.position() + length * 2)
                    } else {
                        val modelIds = arrayOfNulls<IntArray>(1)
                        modelIds[0] = IntArray(length)
                        modelTypes = ByteArray(length)
                        repeat(length) { index ->
                            modelIds[0]!![index] = buffer.readUnsignedShort()
                        }
                        this.modelIds = modelIds.filterNotNull().toTypedArray()

                    }
                }
            }
            14 -> sizeX = buffer.readUnsignedByte()
            15 -> sizeY = buffer.readUnsignedByte()
            17 -> {
                projectileClipped = false
                solid = 0
            }
            18 -> projectileClipped = false
            19 -> interactive = buffer.readUnsignedByte()
            21 -> contouredGround = 0//
            22 -> delayShading = true
            23 -> culling = 1
            24 -> {
                var animation = buffer.readUnsignedShort()
                if (animation == 65535) {
                    animation = -1
                }
                animations = intArrayOf(animation)
            }
            27 -> solid = 1
            28 -> offsetMultiplier = buffer.readUnsignedByte()// shl 2
            29 -> brightness = buffer.readByte()
            in 30..34 -> {
                options!![opcode - 30] = buffer.readString()
                if (options!![opcode - 30].equals("Hidden", true)) {//
                    options!![opcode - 30] = null
                }
            }
            39 -> contrast = buffer.readByte() * 25
            40 -> readColours(buffer)
            41 -> readTextures(buffer)
            42 -> {
                val length = buffer.readUnsignedByte()
                recolourPalette = ByteArray(length)
                repeat(length) { count ->
                    recolourPalette!![count] = buffer.readByte().toByte()
                }
            }
            62 -> mirrored = true
            64 -> castsShadow = false
            65 -> modelSizeX = buffer.readUnsignedShort()//
            66 -> modelSizeY = buffer.readUnsignedShort()//
            67 -> modelSizeZ = buffer.readUnsignedShort()//
            68 -> mapscene = buffer.readUnsignedShort()//
            69 -> blockFlag = buffer.readUnsignedByte()
            70 -> offsetX = buffer.readShort()//
            71 -> offsetY = buffer.readShort()//
            72 -> offsetZ = buffer.readShort()//
            73 -> blocksSky = true
            74 -> swimmable = true
            75 -> supportItems = buffer.readUnsignedByte()
            77, 92 -> {
                varbitIndex = buffer.readUnsignedShort()//
                if (varbitIndex == 65535) {
                    varbitIndex = -1
                }
                configId = buffer.readUnsignedShort()//
                if (configId == 65535) {
                    configId = -1
                }
                var last = -1
                if (opcode == 92) {
                    last = buffer.readUnsignedShort()//
                    if (last == 65535) {
                        last = -1
                    }
                }
                val length = buffer.readUnsignedByte()
                configObjectIds = IntArray(length + 2)
                var count = 0
                while (length >= count) {
                    configObjectIds!![count] = buffer.readUnsignedShort()//
                    if (configObjectIds!![count] == 65535) {
                        configObjectIds!![count] = -1
                    }
                    count++
                }
                configObjectIds!![length + 1] = last
            }
            78 -> {
                anInt3015 = buffer.readShort()
                anInt3012 = buffer.readUnsignedByte()
            }
            79 -> {
                anInt2989 = buffer.readUnsignedShort()//
                anInt2971 = buffer.readUnsignedShort()//
                anInt3012 = buffer.readUnsignedByte()
                val length = buffer.readUnsignedByte()
                anIntArray3036 = IntArray(length)
                repeat(length) { count ->
                    anIntArray3036!![count] = buffer.readUnsignedShort()//
                }
            }
            81 -> {
                contouredGround = 2
                anInt3023 = buffer.readUnsignedByte() * 256
            }
            82 -> mapDefinitionId = buffer.readUnsignedShort()
            249 -> readParameters(buffer)
        }
    }

    override fun ObjectDefinition.changeValues() {
        method4586()
        if (swimmable) {
            solid = 0
            projectileClipped = false
        }
    }


    private fun ObjectDefinition.method4586() {
        if (this.interactive == -1) {
            this.interactive = 0
            if (this.modelTypes != null && (this.modelIds == null || this.modelIds?.get(0)?.get(0) == 10)) {
                this.interactive = 1
            }
            for (i_2 in 0..4) {
                if (this.options?.get(i_2) != null) {
                    this.interactive = 1
                }
            }
        }
        if (this.supportItems == -1) {
            this.supportItems = if (this.solid != 0) 1 else 0
        }
    }
}