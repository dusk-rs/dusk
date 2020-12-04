package rs.dusk.engine.io.strategy

import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.io.StorageStrategy
import rs.dusk.engine.io.jackson.file.FileIO
import rs.dusk.utility.getProperty
import rs.dusk.utility.inject

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @author Greg Hibberd <greg@greghibberd.com>
 *
 * @since April 03, 2020
 */
abstract class YAMLStrategy(

    /**
     * The contents of what will be in the folder
     */
    val contents: String

) : StorageStrategy<Player> {

    /**
     * The io operations to use
     */
    private val io: FileIO by inject()

    /**
     * The path that files will save to
     */
    private fun path(uid: String) = "${getProperty<String>("io_directory")}/${contents}/$uid"

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