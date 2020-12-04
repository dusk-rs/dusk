package rs.dusk.engine.io

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @author Greg Hibberd <greg@greghibberd.com>
 *
 * @since April 03, 2020
 */
abstract class AbstractDataIO<T : Any>(private val strategy: StorageStrategy<T>) {
    open fun load(name: String): T? {
        return strategy.load(name)
    }

    open fun save(name: String, data: T) {
        strategy.save(name, data)
    }
}