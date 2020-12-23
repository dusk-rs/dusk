package rs.dusk.game.world.map.decrypt

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.michaelbull.logging.InlineLogger
import org.koin.dsl.module
import java.io.File


/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class XteaLoader {
	
	private val path = "./data/xteas.json"
	
	private val xteas = hashMapOf<Int, IntArray>()
	
	init {
		load()
	}
	
	fun load() {
		val mapper = ObjectMapper()
		val read = mapper.readValue(File(path), Map::class.java)
			?: throw IllegalStateException("Unable to read xteas from file")
		
		val iterator = read.iterator()
		while (iterator.hasNext()) {
			val next = iterator.next()
			val key : String = next.key as String
			val value = next.value as ArrayList<*>
			val keys = intArrayOf(0, 0, 0, 0)
			
			// must convert Map<String, String> to map <Int, IntArray> [jackson limitations]
			for ((index, v) in value.withIndex()) {
				if (v is Int) {
					keys[index] = v as Int
				}
			}
			val parseInt = Integer.parseInt(key)
			xteas[parseInt] = keys
		}
		logger.info { "${xteas.size} xteas successfully loaded!" }
	}
	
	fun get(regionId : Int) : IntArray {
		return xteas[regionId] ?: throw IllegalStateException("Unable to find xteas in region $regionId")
	}
	
	companion object {
		private val logger = InlineLogger()
	}
	
}

val xteaLoaderModule = module {
	single(createdAtStart = true) { XteaLoader() }
}