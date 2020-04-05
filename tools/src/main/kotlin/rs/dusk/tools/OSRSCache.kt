package rs.dusk.tools

import com.displee.cache.CacheLibrary
import rs.dusk.core.network.codec.packet.access.PacketReader

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 04, 2020
 */
object OSRSCache {
    @JvmStatic
    fun main(args: Array<String>) {
        val rs2 = CacheLibrary("C:\\Users\\Greg\\Documents\\hestia-bundle\\data\\cache\\")
        val osrs = CacheLibrary("C:\\Users\\Greg\\Downloads\\osrs-189\\cache\\")

        val rs2Models = rs2.index(7)
        println(rs2Models.archive(0)?.files?.size)

        println(osrs.index(5).archives().size)
        val regionId = 9008
        val regionX = regionId shr 8
        val regionY = regionId and 0xff
        println("$regionX $regionY")
        val xteas = intArrayOf(
            -1890777568,
            -82183688,
            -453894709,
            1631727865
        )
        val str = "m${regionX}_${regionY}"
        val archive = rs2.index(5).archiveId(str)
        println(archive)
        val data = osrs.data(5, str)!!
        println("Start")
        rs2.put(5, str, data)
        rs2.update()
        println("Complete")
//        osrs.index(7).archives().forEach { archive ->
//            val data = archive.file(0)?.data
//            if(data == null) {
//                return@forEach
//            }
//            println("Write ${archive.id}")
//            rs2.put(7, 0, data)
//        }

//        val mapArchive = regionIndex.findArchiveByName("m${x}_$z") ?: return false
//
//        val mapData = mapArchive.decompress(world.filestore.storage.loadArchive(mapArchive))
//        rs2.index(7).update()
//        println(osrs.isOSRS())
//
//        println(osrs.index(10).info)
//
//        val data = osrs.data(10, 1, 995)
//        println(data)
//
//        val class300_1 = PacketReader(data!!)
//        val i_2 = 0
//        while (true) {
//            val opcode: Int = class300_1.readUnsignedByte()
//            if (opcode == 0) {
//                return
//            }
//            println("Opcode $opcode")
//            readValues(class300_1, opcode)
//        }

    }

    private fun readValues(class300_1: PacketReader, i_2: Int) {
        if (i_2 == 1) {
            val modelId = class300_1.readUnsignedShort()
        } else if (i_2 == 2) {
            val name = class300_1.readString()
            println(name)
        } else if (i_2 == 4) {
            val spriteScale = class300_1.readUnsignedShort()
        } else if (i_2 == 5) {
            val spritePitch = class300_1.readUnsignedShort()
        } else if (i_2 == 6) {
            val spriteCameraRoll = class300_1.readUnsignedShort()
        } else if (i_2 == 7) {
            var spriteTranslateX = class300_1.readUnsignedShort()
            if (spriteTranslateX > 32767) {
                spriteTranslateX -= 65536
            }
        } else if (i_2 == 8) {
            var spriteTranslateY = class300_1.readUnsignedShort()
            if (spriteTranslateY > 32767) {
                spriteTranslateY -= 65536
            }
        } else if (i_2 == 11) {
            val stackable = 1
        } else if (i_2 == 12) {
            val field3428 = class300_1.readInt()
        } else if (i_2 == 16) {
            val members = true
        } else if (i_2 == 23) {
            val primaryMaleModel = class300_1.readUnsignedShort()
            val maleWieldY = class300_1.readUnsignedByte()
        } else if (i_2 == 24) {
            val secondaryMaleModel = class300_1.readUnsignedShort()
        } else if (i_2 == 25) {
            val primaryFemaleModel = class300_1.readUnsignedShort()
            val femaleWieldY = class300_1.readUnsignedByte()
        } else if (i_2 == 26) {
            val secondaryFemaleModel = class300_1.readUnsignedShort()
        } else if (i_2 >= 30 && i_2 < 35) {
            val floorItems = arrayOfNulls<String>(5)
            floorItems[i_2 - 30] = class300_1.readString()
            if (floorItems[i_2 - 30].equals("Hidden", ignoreCase = true)) {
                floorItems[i_2 - 30] = null
            }
        } else if (i_2 >= 35 && i_2 < 40) {
            val options = arrayOfNulls<String>(5)
            options[i_2 - 35] = class300_1.readString()
        } else {
            val i_4: Int
            var i_5: Int
            if (i_2 == 40) {
                i_4 = class300_1.readUnsignedByte()
                val originalColours = ShortArray(i_4)
                val replacementColours = ShortArray(i_4)
                i_5 = 0
                while (i_5 < i_4) {
                    originalColours[i_5] = class300_1.readUnsignedShort().toShort()
                    replacementColours[i_5] = class300_1.readUnsignedShort().toShort()
                    i_5++
                }
            } else if (i_2 == 41) {
                i_4 = class300_1.readUnsignedByte()
                val originalTextures = ShortArray(i_4)
                val replacementTextures = ShortArray(i_4)
                i_5 = 0
                while (i_5 < i_4) {
                    originalTextures[i_5] = class300_1.readUnsignedShort().toShort()
                    replacementTextures[i_5] = class300_1.readUnsignedShort().toShort()
                    i_5++
                }
            } else if (i_2 == 42) {
                val shiftClickDropIndex = class300_1.readByte()
            } else if (i_2 == 65) {
                val tradable = true
            } else if (i_2 == 78) {
                val tertiaryMaleModel = class300_1.readUnsignedShort()
            } else if (i_2 == 79) {
                val tertiaryFemaleModel = class300_1.readUnsignedShort()
            } else if (i_2 == 90) {
                val primaryMaleDialogueHead = class300_1.readUnsignedShort()
            } else if (i_2 == 91) {
                val primaryFemaleDialogueHead = class300_1.readUnsignedShort()
            } else if (i_2 == 92) {
                val secondaryMaleDialogueHead = class300_1.readUnsignedShort()
            } else if (i_2 == 93) {
                val secondaryFemaleDialogueHead = class300_1.readUnsignedShort()
            } else if (i_2 == 95) {
                val spriteCameraYaw = class300_1.readUnsignedShort()
            } else if (i_2 == 97) {
                val noteId = class300_1.readUnsignedShort()
            } else if (i_2 == 98) {
                val notedTemplateId = class300_1.readUnsignedShort()
            } else if (i_2 >= 100 && i_2 < 110) {
                var stackIds: IntArray? = null
                var stackAmounts: IntArray? = null
                if (stackIds == null) {
                    stackIds = IntArray(10)
                    stackAmounts = IntArray(10)
                }
                stackIds[i_2 - 100] = class300_1.readUnsignedShort()
                stackAmounts!![i_2 - 100] = class300_1.readUnsignedShort()
            } else if (i_2 == 110) {
                val floorScaleX = class300_1.readUnsignedShort()
            } else if (i_2 == 111) {
                val floorScaleY = class300_1.readUnsignedShort()
            } else if (i_2 == 112) {
                val floorScaleZ = class300_1.readUnsignedShort()
            } else if (i_2 == 113) {
                val ambience = class300_1.readByte()
            } else if (i_2 == 114) {
                val diffusion = class300_1.readByte()
            } else if (i_2 == 115) {
                val team = class300_1.readUnsignedByte()
            } else if (i_2 == 139) {
                val boughtLink = class300_1.readUnsignedShort()
            } else if (i_2 == 140) {
                val boughtTemplate = class300_1.readUnsignedShort()
            } else if (i_2 == 148) {
                val placeholderId = class300_1.readUnsignedShort()
            } else if (i_2 == 149) {
                val placeholderTemplateId = class300_1.readUnsignedShort()
            } else if (i_2 == 249) {
//                val parameters = class26.method403(class300_1, this.parameters, -675682767)
                val length: Int = class300_1.readUnsignedByte()
                val params = hashMapOf<Int, Any>()
                for (i in 0 until length) {
                    val isString = class300_1.readUnsignedByte() == 1
                    val key: Int = class300_1.readMedium()
                    var value: Any
                    value = if (isString) {
                        class300_1.readString()
                    } else {
                        class300_1.readInt()
                    }
                    params[key] = value
                }
            }
        }
    }
}