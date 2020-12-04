package rs.dusk.engine.io

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @author Greg Hibberd <greg@greghibberd.com>
 *
 * @since April 03, 2020
 */
interface StorageStrategy<T : Any> : IO {

    /**
     * The io operations to use
     */
    val io: IO

    /**
     * The path that files will save to
     */
    fun path(uid: String): String

}