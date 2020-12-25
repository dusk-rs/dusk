package rs.dusk.game.entity.character.data.skill

import rs.dusk.engine.client.send
import rs.dusk.engine.entity.character.player.skill.Skill
import rs.dusk.game.container.player.chat.ChatType
import rs.dusk.game.entity.character.player.Player
import rs.dusk.network.codec.game.encode.message.ChatMessage
import rs.dusk.utility.Maths.interpolate
import kotlin.random.Random

object Level {
	
	const val MIN_LEVEL = 1
	const val MAX_LEVEL = 99
	private const val MAX_CHANCE = 256
	
	/**
	 * Calculates random chance of being successful
	 * @param level The players current level
	 * @param chances The chance rates (out of [MAX_CHANCE]) at level 1 and 99
	 * @return success
	 */
	fun success(level : Int, chances : IntRange) : Boolean {
		val chance = getChance(level, chances)
		val random = Random.nextInt(MAX_CHANCE)
		return chance > random
	}
	
	/**
	 * The chance of being successful (out of [MAX_CHANCE])
	 */
	fun getChance(level : Int, chances : IntRange) : Int {
		return interpolate(level.coerceIn(MIN_LEVEL, MAX_LEVEL), chances.first, chances.last, MIN_LEVEL, MAX_LEVEL)
	}
	
	fun Player.has(skill : Skill, level : Int, message : Boolean = false) : Boolean {
		if (levels.get(skill) < level) {
			if (message) {
				message("You need to have an ${skill.name} level of $level.")
			}
			return false
		}
		return true
	}
	
	fun Player.message(text : String, type : ChatType = ChatType.Game, tile : Int = 0, name : String? = null) {
		send(ChatMessage(type.id, tile, text, name, name?.toLowerCase()?.replace(" ", "_")))
	}
	
}