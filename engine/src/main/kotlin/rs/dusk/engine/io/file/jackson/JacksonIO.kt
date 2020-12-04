package rs.dusk.engine.io.file.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.github.michaelbull.logging.InlineLogger
import org.koin.dsl.module
import rs.dusk.engine.io.IO
import rs.dusk.utility.func.FileFunction
import java.io.File
import kotlin.reflect.KClass

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @author Greg Hibberd <greg@greghibberd.com>
 *
 * @since April 03, 2020
 */
open class JacksonIO(private val quotes : Boolean = false) : IO {
	
	private val logger = InlineLogger()
	
	/**
	 * The instance of the mapper
	 */
	val mapper = ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER).apply {
		if (!quotes) {
			enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
		}
	})
	
	init {
		mapper.findAndRegisterModules()
		logger.info { "YAML file mapper loaded." }
	}
	
	/**
	 * Loads data from an object mapper
	 */
	inline fun <reified T : Any> read(path : String) : T {
		val file = FileFunction.find(path)
		return mapper.readValue(file, T::class.java)
	}
	
	/**
	 * Loads data from an object mapper where possible
	 */
	inline fun <reified T : Any> readNullable(path : String) : T? {
		val file = File(path)
		return if (file.exists()) {
			try {
				mapper.readValue(file, T::class.java)
			} catch (e : MismatchedInputException) {
				e.printStackTrace()
				null
			}
		} else {
			null
		}
	}
	
	override fun read(path : String, data : KClass<Any>) : Any {
		return mapper.readValue<Any>(File(path), data.java)
	}
	
	override fun write(path : String, identifier : String, data : Any) {
		val location = "$path$identifier"
		val dataString = mapper.writeValueAsString(data)
		println("path = [${path}], identifier = [${identifier}], data = [${data}], location=$location")
		FileFunction.write(location, dataString)
	}
	
}

val jacksonIOModule = module { single(createdAtStart = true) { JacksonIO() } }