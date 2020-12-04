package rs.dusk.engine.io.player.strategy

import org.koin.dsl.module
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.io.StorageStrategy
import rs.dusk.engine.io.file.FileIO
import rs.dusk.utility.inject

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @author Greg Hibberd <greg@greghibberd.com>
 *
 * @since April 03, 2020
 */
class YMLStrategy(private val path: String) : StorageStrategy<Player> {

    /**
     * The path that files will save to
     */
    private fun path(name: String) = "./data/io/$path/$name.yml"

    /**
     * The io operations to use
     */
    private val io: FileIO by inject()

    /**
     * Load the player data from the yml file
     */
    override fun load(uid: String): Player? {
        return io.loadOrNull(path(uid))
    }

    /**
     * Save the player data to a file
     */
    override fun save(uid: String, data: Player) {
        return io.save(path(uid), data)
    }

}

@Suppress("USELESS_CAST")
val ymlPlayerModule = module {
    single {
        YMLStrategy(getProperty("savePath"))
    }
}
