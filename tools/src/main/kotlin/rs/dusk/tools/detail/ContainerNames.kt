package rs.dusk.tools.detail

import org.koin.core.context.startKoin
import rs.dusk.engine.client.cacheDefinitionModule
import rs.dusk.engine.client.cacheModule
import rs.dusk.engine.entity.DefinitionsDecoder.Companion.toIdentifier
import rs.dusk.engine.io.file.jackson.yaml.YamlIO
import java.io.File

/**
 * Dumps unique string identifiers for containers using shop csv
 */
private object ContainerNames {
	
	private data class Data(
		val name : String,
		val container : Int?,
		val sampleContainer : Int?,
		val skillcapeContainer : Int?,
		val trimmedContainer : Int?
	)
	
	private data class Ids(val id : Int)
	
	@JvmStatic
	fun main(args : Array<String>) {
		val koin = startKoin {
			fileProperties("/tool.properties")
			modules(cacheModule, cacheDefinitionModule)
		}.koin
		
		val file = File("./shop-info-667.csv")
		
		val map = mutableMapOf<Int, String>()
		
		file.readLines().map { it.split(",") }.map {
			Data(it[0], it[5].toIntOrNull(), it[6].toIntOrNull(), it[7].toIntOrNull(), it[8].toIntOrNull())
		}.forEach {
			if (it.container != null) {
				map[it.container] = toIdentifier(it.name)
			}
			if (it.sampleContainer != null) {
				map[it.sampleContainer] = "${toIdentifier(it.name)}_sample"
			}
			if (it.skillcapeContainer != null) {
				map[it.skillcapeContainer] = "${toIdentifier(it.name)}_skillcape"
			}
			if (it.trimmedContainer != null) {
				map[it.trimmedContainer] = "${toIdentifier(it.name)}_trimmed"
			}
		}
		println(map)
		val yamlIO : YamlIO<Map<String, ContainerNames.Ids>> = koin.get()
		val path = ""
		val sorted = map.map { it.value to Ids(it.key) }.sortedBy { it.second.id }.toMap()
		yamlIO.write(sorted)
		println("${sorted.size} container identifiers dumped to $path.")
	}
	
}