package rs.dusk.engine.io.file.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import org.koin.dsl.module
import rs.dusk.engine.io.IO
import java.io.File

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @author Greg Hibberd <greg@greghibberd.com>
 *
 * @since April 03, 2020
 */
open class JacksonIO(
    /**
     * The instance of the mapper
     */
    val mapper: ObjectMapper = ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))

) : IO {

    /**
     * Loads data from an object mapper
     */
    inline fun <reified T : Any> read(path: String) = mapper.readValue(File(path), T::class.java)

    override fun read(path: String, `class`: Class<Any>): Any {
        return mapper.readValue<Any>(File(path), `class`)
    }

    override fun write(path: String, identifier: String, data: Any) {
        mapper.writeValue(File(path), data)
    }

}

val jacksonIOModule = module { single(createdAtStart = true) { JacksonIO() } }