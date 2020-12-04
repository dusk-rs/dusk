package rs.dusk.engine.io.file

import org.koin.dsl.module
import rs.dusk.engine.io.file.jackson.JacksonIO

/**
 * @author Tyluur <itstyluur@gmail.com>
 *
 * This class is responsible for all operations done on local files.
 * Dusk utilizes YAML flat files for all configuration.
 *
 * @since April 03, 2020
 */
class FileIO : JacksonIO() {

    fun generateFilePath(path: String, identifier: String) = "${path}${identifier}.yml"

}

val fileIO = module { single(createdAtStart = true) { FileIO() } }