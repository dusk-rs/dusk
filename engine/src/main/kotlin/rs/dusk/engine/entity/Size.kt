package rs.dusk.engine.entity

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 18, 2020
 */
data class Size(val width: Int, val height: Int) {
    companion object {
        val TILE = Size(1, 1)
    }
}