package org.redrune.cache.definition.encoder

import org.junit.jupiter.api.Test
import org.redrune.cache.definition.data.ObjectDefinition
import org.redrune.core.io.write.BufferWriter

/**
 * @author Greg Hibberd <greg></greg>@greghibberd.com>
 * @since April 13, 2020
 */
internal class ObjectEncoderTest {


    @Test
    fun `Encode full test`() {
        val definition = ObjectDefinition(
            0,
            modelIds = arrayOf(intArrayOf(1, 2, 35000), intArrayOf(60000, 1, 2)),
            modelTypes = byteArrayOf(1, 2, 3),
            name = "Test Object",
            sizeX = 2,
            sizeY = 2,
            projectileClipped = false,
            solid = 1,
            interactive = 0,
            contouredGround = 1,
            delayShading = true,
            offsetMultiplier = 128,
            brightness = 1,
            options = arrayOf("Take", "Eat", "Stop", "Kick", "Speak"),
            contrast = 1,
            originalColours = shortArrayOf(14000, 15000, 16000),
            modifiedColours = shortArrayOf(14000, 15000, 16000),
            originalTextureColours = shortArrayOf(14000, 15000, 16000),
            modifiedTextureColours = shortArrayOf(14000, 15000, 16000),
            recolourPalette = byteArrayOf(0, 1, 2, 3, 4),
            mirrored = true,
            castsShadow = false,
            modelSizeX = 64,
            modelSizeY = 82,
            modelSizeZ = 32,
            blockFlag = 1,
            offsetX = 10,
            offsetY = 32,
            offsetZ = 100,
            blocksSky = true,
            swimmable = true,
            supportItems = 0,
            varbitIndex = 10,
            configId = 1,
            configObjectIds = intArrayOf(100, 10, 1),
            anInt3015 = 0,
            anInt3012 = 1,
            anInt2989 = 1,
            anInt2971 = 1,
            anIntArray3036 = intArrayOf(1, 2, 3),
            anInt3023 = 1,
            aBoolean2990 = true,
            aBoolean2972 = false,
            animateImmediately = false,
            isMembers = true,
            aBoolean3056 = true,
            aBoolean2998 = true,
            anInt2987 = 0,
            anInt3008 = 0,
            anInt3038 = 0,
            anInt3013 = 0,
            anInt2958 = 1,
            mapscene = 1,
            culling = 0,
            anInt3024 = 0,
            invertMapScene = true,
            animations = intArrayOf(1, 1000, 10000),
            anIntArray2995 = intArrayOf(10000, 1000, 100),
            mapDefinitionId = 0,
            anIntArray2981 = intArrayOf(1, 2, 3),
            aByte2974 = 0,
            aByte3045 = 0,
            aByte3052 = 0,
            aByte2960 = 0,
            anInt2964 = 0,
            anInt2963 = 0,
            anInt3018 = 0,
            anInt2983 = 0,
            aBoolean2961 = true,
            aBoolean2993 = true,
            anInt3032 = 1012,
            anInt2962 = 1,
            anInt3050 = 512,
            anInt3020 = 512,
            aBoolean2992 = true,
            anInt2975 = 10,
            params = hashMapOf(1L to "string", 2L to 100000)
        )

        val encoder = ObjectEncoder()

        val writer = BufferWriter()
        with(encoder) {
            writer.encode(definition)
        }
        val data = writer.buffer.array()
        val expected = byteArrayOf(
            1,
            2,
            1,
            3,
            0,
            1,
            0,
            2,
            -120,
            -72,
            2,
            3,
            -22,
            96,
            0,
            1,
            0,
            2,
            2,
            84,
            101,
            115,
            116,
            32,
            79,
            98,
            106,
            101,
            99,
            116,
            0,
            14,
            2,
            15,
            2,
            18,
            19,
            0,
            21,
            22,
            24,
            0,
            1,
            27,
            28,
            32,
            29,
            1,
            30,
            84,
            97,
            107,
            101,
            0,
            31,
            69,
            97,
            116,
            0,
            32,
            83,
            116,
            111,
            112,
            0,
            33,
            75,
            105,
            99,
            107,
            0,
            34,
            83,
            112,
            101,
            97,
            107,
            0,
            39,
            0,
            40,
            3,
            54,
            -80,
            54,
            -80,
            58,
            -104,
            58,
            -104,
            62,
            -128,
            62,
            -128,
            41,
            3,
            54,
            -80,
            54,
            -80,
            58,
            -104,
            58,
            -104,
            62,
            -128,
            62,
            -128,
            42,
            5,
            0,
            1,
            2,
            3,
            4,
            62,
            64,
            65,
            0,
            64,
            66,
            0,
            82,
            67,
            0,
            32,
            69,
            1,
            70,
            0,
            2,
            71,
            0,
            8,
            72,
            0,
            25,
            73,
            74,
            75,
            0,
            92,
            0,
            10,
            0,
            1,
            0,
            1,
            1,
            0,
            100,
            0,
            10,
            78,
            0,
            0,
            1,
            79,
            0,
            1,
            0,
            1,
            1,
            3,
            0,
            1,
            0,
            2,
            0,
            3,
            82,
            88,
            89,
            91,
            97,
            98,
            99,
            0,
            0,
            0,
            100,
            0,
            0,
            0,
            101,
            1,
            102,
            0,
            1,
            103,
            104,
            0,
            105,
            106,
            3,
            0,
            1,
            16,
            3,
            -24,
            -24,
            39,
            16,
            100,
            107,
            0,
            0,
            -96,
            3,
            0,
            1,
            0,
            2,
            0,
            3,
            -88,
            -87,
            -86,
            -125,
            -12,
            -85,
            1,
            -83,
            2,
            0,
            2,
            0,
            -79,
            -78,
            10,
            -7,
            2,
            1,
            0,
            0,
            1,
            115,
            116,
            114,
            105,
            110,
            103,
            0,
            0,
            0,
            0,
            2,
            0,
            1,
            -122,
            -96,
            0,
            0,
            0,
            0,
            0
        )
        assert(data.contentEquals(expected))
    }

}