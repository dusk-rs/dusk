package rs.dusk.engine.io.file

import org.koin.dsl.module
import rs.dusk.engine.io.file.jackson.YAMLIO

/**
 * @author Tyluur <itstyluur@gmail.com>
 *
 * This class is responsible for all operations done on local files.
 * Dusk utilizes YAML flat files for all configuration.
 *
 * @since April 03, 2020
 */
class FileIO : YAMLIO() {
	
	fun generateFilePath(path : String) = "${path}"
	
}

val fileIO = module { single(createdAtStart = true) { FileIO() } }