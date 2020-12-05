package rs.dusk.engine.io.file.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import rs.dusk.engine.io.file.FileIO
import java.io.File

/**
 * @author Tyluur <contact@kiaira.tech>
 *
 * @since December 04, 2020
 */
interface JacksonIO<T: Any> : FileIO {
	
	/**
	 * The mapper
	 */
	fun mapper() : ObjectMapper
	
	/**
	 * The strategy to write data
	 */
	fun write(data : T) : File
	
	/**
	 * The strategy to read data
	 *
	 * @param identifier The location that the data is coming from
	 *
	 * @return Any The data type of the data that was read
	 */
	fun <T> read(identifier : String) : T
}