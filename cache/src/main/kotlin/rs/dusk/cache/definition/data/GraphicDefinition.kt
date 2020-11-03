package rs.dusk.cache.definition.data

import rs.dusk.cache.Definition
import rs.dusk.cache.definition.Details
import rs.dusk.cache.definition.Recolourable

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 07, 2020
 */
@Suppress("ArrayInDataClass")
data class GraphicDefinition(
    override var id: Int = -1,
    var modelId: Int = 0,
    var animationId: Int = -1,
    var sizeXY: Int = 128,
    var sizeZ: Int = 128,
    var rotation: Int = 0,
    var ambience: Int = 0,
    var contrast: Int = 0,
    var aByte2381: Byte = 0,
    var anInt2385: Int = -1,
    var aBoolean2402: Boolean = false,
    override var originalColours: ShortArray? = null,
    override var modifiedColours: ShortArray? = null,
    override var originalTextureColours: ShortArray? = null,
    override var modifiedTextureColours: ShortArray? = null,
    override var details: Map<String, Any> = emptyMap()
) : Definition, Recolourable, Details