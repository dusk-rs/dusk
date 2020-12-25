package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * A chat box message to display
 * @param type The message type
 * @param tile The tile the message was sent from
 * @param name Optional display name?
 * @param message The chat message text
 */
data class ChatMessage(
	val type : Int,
	val tile : Int,
	val message : String,
	val name : String?,
	val formatted : String?
) : GameMessage