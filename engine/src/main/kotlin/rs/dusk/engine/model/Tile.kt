package rs.dusk.engine.model

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 28, 2020
 */
data class Tile(val x: Int, val y: Int, val plane: Int = 0) {

    constructor(id: Int) : this(id shr 14 and 0x3fff, id and 0x3fff, id shr 28)

    val id by lazy { (y and 0x3fff) + ((x and 0x3fff) shl 14) + ((plane and 0x3) shl 28) }
    val chunk by lazy { Chunk(x / 8, y / 8) }
    val region by lazy { Region(x / 64, y / 64) }
    val regionPlane by lazy { RegionPlane(x / 64, y / 64, plane) }

    fun equals(x: Int, y: Int, plane: Int = 0) = this.x == x && this.y == y && this.plane == plane

    companion object {

        fun createSafe(x: Int, y: Int, plane: Int = 0) = Tile(x and 0x3fff, y and 0x3fff, plane and 0x3)

        fun Tile.add(x: Int = 0, y: Int = 0, plane: Int = 0): Tile {
            return Tile(this.x + x, this.y + y, this.plane + plane)
        }
    }
}