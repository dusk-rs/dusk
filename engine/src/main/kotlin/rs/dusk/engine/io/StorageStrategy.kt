package rs.dusk.engine.io

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @author Tyluur <itstyluur@gmail.com>
 * @since April 03, 2020
 */
interface StorageStrategy<T : Any> {

    /**
     * The strategy to load data
     */
    fun load(uid: String): T?

    /**
     * The strategy to save data
     */
    fun save(uid: String, data: T)
}