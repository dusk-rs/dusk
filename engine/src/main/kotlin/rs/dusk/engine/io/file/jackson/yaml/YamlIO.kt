package rs.dusk.engine.io.file.jackson.yaml

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import rs.dusk.engine.io.file.jackson.JacksonIO

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @author Greg Hibberd <greg@greghibberd.com>
 *
 * @since April 03, 2020
 */
interface YamlIO<T : Any> : JacksonIO<T> {
	
	/**
	 * The instance of the mapper
	 */
	override fun mapper() = ObjectMapper(YAMLFactory()).apply {
		writerWithDefaultPrettyPrinter()
		findAndRegisterModules()
	}
	
	fun generatePath(identifier : String) = "${location()}$identifier.yml"
	
}