package rs.dusk.utility.io

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.reflect.Modifier

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 18, 2020
 */
object JsonFile {
	/**
	 * The gson instance
	 */
	private val GSON : Gson = GsonBuilder().setPrettyPrinting().create()
	
	/**
	 * Loads the file data
	 *
	 * @param file
	 * The file to load data from
	 */
	fun <K> loadJsonData(file : File) : K? {
		return if (!file.exists()) {
			throw IllegalStateException("Unable to load data from file, it did not exist! [file=$file]")
		} else GSON.fromJson(FileIO.getText(file.absolutePath), object : TypeToken<K>() {}.type)
	}
	
	/**
	 * Saves the data to the file
	 *
	 * @param data
	 * The list to save
	 * @param location
	 * The location to save to
	 */
	fun <T> save(data : T, location : String?) : Boolean {
		try {
			FileWriter(location).use { writer ->
				val builder : GsonBuilder = GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
					.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
				val gson : Gson = builder.create()
				gson.toJson(data, writer)
				return true
			}
		} catch (e : IOException) {
			e.printStackTrace()
			return false
		}
	}
}
