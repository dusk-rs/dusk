package rs.dusk.game.model.player

import com.github.michaelbull.logging.InlineLogger
import org.koin.dsl.module
import rs.dusk.engine.client.send
import rs.dusk.engine.client.ui.InterfaceManager
import rs.dusk.engine.client.ui.InterfaceOptions
import rs.dusk.engine.client.ui.PlayerInterfaceIO
import rs.dusk.engine.client.ui.detail.InterfaceDetails
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.entity.definition.ContainerDefinitions
import rs.dusk.engine.event.EventBus
import rs.dusk.engine.io.file.FileIO
import rs.dusk.engine.io.file.jackson.yaml.YamlIO
import rs.dusk.engine.map.Tile
import rs.dusk.engine.map.collision.Collisions
import rs.dusk.engine.path.strat.FollowTargetStrategy
import rs.dusk.engine.path.strat.RectangleTargetStrategy
import rs.dusk.network.rs.codec.game.encode.message.SkillLevelMessage
import rs.dusk.utility.func.FileFunction
import rs.dusk.utility.get
import rs.dusk.utility.getProperty
import rs.dusk.utility.inject
import java.io.File
import java.io.FileNotFoundException

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @author Greg Hibberd <greg@greghibberd.com>
 *
 * @since April 03, 2020
 */
class PlayerIO : YamlIO<Player> {
	
	private val logger = InlineLogger()
	
	private val x = getProperty("homeX", 0)
	private val y = getProperty("homeY", 0)
	private val plane = getProperty("homePlane", 0)
	private val tile = Tile(x, y, plane)
	
	private val bus : EventBus = get()
	private val collisions : Collisions = get()
	private val definitions : ContainerDefinitions = get()
	private val interfaces : InterfaceDetails = get()
	
	private val fileIO : FileIO by inject()
	private val yamlIO : YamlIO<Player> by inject()
	
	override fun location() : String {
		return "${getProperty<String>("local_files_path")}players/"
	}
	
	override fun <Player> read(identifier : String) : Player {
		val path = yamlIO.generatePath(identifier)
		val file = fileIO.find(path)
		
		val player : Player = try {
			yamlIO.read<Player>(path)
		} catch (e : FileNotFoundException) {
			Player(id = -1, tile = tile)
		} catch (e : Exception) {
			e.printStackTrace()
			null
		} as Player? ?: throw IllegalStateException("Unable to find player")
		
		logger.info { "" }
		
		return player
	}
	
	override fun write(player : Player) : File {
		val dataString =
			try {
				mapper().writeValueAsString(player)
			} catch (e : Exception) {
				e.printStackTrace()
				""
			}
		return FileFunction.write(location(), dataString)
	}
	
	/**
	 * Loads a player's file
	 */
	fun load(login : String) : Player {
		val player = read<Player>(login)
		
		player.login = login
		logger.info { "Found player [player=$player]" }
		
		bind(player)
		logger.info { "Bound player [player=$player]" }
		
		save(player)
		return player
	}
	
	/**
	 * Saves a player's file
	 */
	fun save(player : Player) : File {
		val identifier = player.login
		val path = yamlIO.generatePath(identifier)
		return yamlIO.write(player)
	}
	
	/**
	 * Bind all necessary components of the player
	 */
	private fun bind(player : Player) : Player {
		val interfaceIO = PlayerInterfaceIO(player, bus)
		player.interfaces = InterfaceManager(interfaceIO, interfaces, player.gameFrame)
		player.interfaceOptions = InterfaceOptions(player, interfaces, definitions)
		player.experience.addListener { skill, _, experience ->
			val level = player.levels.get(skill)
			player.send(SkillLevelMessage(skill.ordinal, level, experience.toInt()))
		}
		player.interactTarget = RectangleTargetStrategy(collisions, player)
		player.followTarget = FollowTargetStrategy(player)
		return player
	}
	
}

val playerIO = module { single(createdAtStart = true) { PlayerIO() } }