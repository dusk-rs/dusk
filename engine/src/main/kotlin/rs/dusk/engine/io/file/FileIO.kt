package rs.dusk.engine.io.file

import rs.dusk.engine.io.IO
import rs.dusk.utility.func.FileFunction
import java.io.File

/**
 * @author Tyluur <itstyluur@gmail.com>
 *
 * This class is responsible for all operations done on local files.
 * Dusk utilizes YAML flat files for all configuration.
 *
 * @since April 03, 2020
 */
interface FileIO : IO {
	
	/**
	 * The location of the file
	 */
	fun location() : String
	
	/**
	 * Finds a file by a path
	 */
	fun find(path : String) : File {
		return FileFunction.find(path)
	}
}