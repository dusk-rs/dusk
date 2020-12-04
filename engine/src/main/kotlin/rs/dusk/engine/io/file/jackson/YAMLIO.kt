package rs.dusk.engine.io.file.jackson

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_SELF_REFERENCES
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
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
open class YAMLIO(private val quotes : Boolean = false) : IO {
	
	private val logger = InlineLogger()
	
	/**
	 * The instance of the mapper
	 */
	val mapper = ObjectMapper(YAMLFactory()).apply {
		configure(MapperFeature.USE_ANNOTATIONS, true)
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
		configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
		configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true)
		writerWithDefaultPrettyPrinter()
	}
	
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
		try {
			val location = "$path$identifier.yml"
			val yml = try {
				mapper.writeValueAsString(data)
			} catch (e : Exception) {
				e.printStackTrace()
				null
			}
			val dataString = yml ?: ""
			
			FileFunction.write(location, dataString)
		} catch (e : Exception) {
			e.printStackTrace()
		}
	}
	
}

val jacksonIOModule = module { single(createdAtStart = true) { YAMLIO() } }