package rs.dusk.engine.io

import kotlin.reflect.KClass

/**
 * @author Tyluur <itstyluur@gmail.com>
 *
 * @since December 4th, 2020
 */
interface IO {

    /**
     * The strategy to read data
     *
     * @param path The path that the data is coming from
     *
     * @return Any The data type of the data that was read
     */
    fun read(path: String, `class`: KClass<Any>): Any

    /**
     * The strategy to write data
     */
    fun write(path: String, identifier: String, data: Any)
}