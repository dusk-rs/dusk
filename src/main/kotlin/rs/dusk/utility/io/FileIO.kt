package rs.dusk.utility.io

import java.io.BufferedReader
import java.io.File

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 18, 2020
 */
object FileIO {
	
	/**
	 * Getting the text in the file as a formatted `String` `Object`
	 *
	 * @param location
	 * The location of the file
	 */
	fun getText(location : String) : String {
		val bufferedReader : BufferedReader = File(location).bufferedReader()
		return bufferedReader.use { it.readText() }
	}
	
	
}