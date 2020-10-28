package rs.dusk.tools.detail

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.runBlocking
import org.apache.commons.io.IOUtils
import org.koin.core.context.startKoin
import rs.dusk.cache.definition.decoder.NPCDecoder
import rs.dusk.engine.client.cacheDefinitionModule
import rs.dusk.engine.client.cacheModule
import rs.dusk.engine.data.file.FileLoader
import rs.dusk.engine.data.file.fileLoaderModule
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


/**
 * @author Jacob Rhiel <jacob.rhiel@gmail.com>
 * @created Oct 28, 2020
 */
object BestiaryDetailDumper
{

    @JsonIgnoreProperties("name", allowSetters = true)
    private data class Beast(
        val name: String,
        val id: Int,
        val members: Boolean,
        val weakness: String?,
        val level: Int,
        val lifepoints: Int,
        val defence: Int,
        val attack: Int,
        val magic: Int,
        val ranged: Int,
        val xp: String,
        val slayerlevel: Int,
        val slayercat: String?,
        val size: Int,
        val attackable: Boolean,
        val aggressive: Boolean,
        val poisonous: Boolean,
        val description: String,
        val area: Array<String> = arrayOf("undefined"),
        val animations: Map<String, Int>
    )

    @JvmStatic
    fun main(args: Array<String>) {

        val koin = startKoin {
            fileProperties("/tool.properties")
            modules(cacheModule, cacheDefinitionModule, fileLoaderModule)
        }.koin
        val decoder = NPCDecoder(koin.get(), member = true)
        val loader: FileLoader = koin.get()
        val names = NPCNames(decoder)

        val entities = names.getNamedEntities(decoder.size)
        val unique = names.getUniqueList(entities)
        val sorted = unique.toList().sortedBy { it.second["id"] as Int }.toMap()

        val beasts = mutableMapOf<String, Beast>()

        runBlocking {

            //names.dump(loader, "./npc-details.yml", "npc", decoder.size)

            unique.toList().sortedBy { it.second["id"] as Int }.subList(0, 10).forEach {

                //kotlinx.coroutines.delay(50)

                val url = URL(
                    "https://secure.runescape.com/m=itemdb_rs/bestiary/beastData.json?beastid=${
                        Integer.valueOf(
                            it.second["id"] as Int
                        )
                    }"
                )

                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"

                val `in`: InputStream = connection.inputStream

                val encoding: String = connection.contentEncoding ?: "UTF-8"

                val body: String = IOUtils.toString(`in`, encoding)

                val mapper = jacksonObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .registerModule(KotlinModule())

                val beast = mapper.readValue(JsonFactory().createParser(body), Beast::class.java)

                println(beast)

                beasts[it.first] = beast

            }

        }

        loader.save("./npc-beast.yml", beasts)

    }

}