package rs.dusk.engine.io.jackson.file

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import org.koin.dsl.module
import rs.dusk.engine.io.jackson.JacksonIO

/**
 * @author Tyluur <itstyluur@gmail.com>
 *
 * This class is responsible for all operations done on local files.
 * Dusk utilizes YAML flat files for all configuration.
 *
 * @since April 03, 2020
 */
class FileIO(private val minimizeQuotes: Boolean = false) : JacksonIO() {

    override
    val mapper: ObjectMapper =
        ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER).apply {
            if (minimizeQuotes) {
                enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
            }
        })

}

val fileIOModule = module { single(createdAtStart = true) { FileIO(false) } }