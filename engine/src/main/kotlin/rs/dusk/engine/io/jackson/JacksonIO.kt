package rs.dusk.engine.io.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import java.io.File

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @author Greg Hibberd <greg@greghibberd.com>
 *
 * @since April 03, 2020
 */
abstract class JacksonIO {

    /**
     * The instance of the mapper
     */
    abstract val mapper: ObjectMapper

    /**
     * Loads data from an object mapper
     */
    inline fun <reified T : Any> load(path: String) = mapper.readValue(File(path), T::class.java)

    /**
     * Loads data from an object mapper where possible
     */
    inline fun <reified T : Any> loadOrNull(path: String): T? {
        val file = File(path)
        return if (file.exists()) {
            try {
                mapper.readValue(file, T::class.java)
            } catch (e: MismatchedInputException) {
                e.printStackTrace()
                null
            }
        } else {
            null
        }
    }

    /**
     * Saves data to a file
     */
    fun <T : Any> save(path: String, data: T) {
        val file = File(path)
        return mapper.writeValue(file, data)
    }

}