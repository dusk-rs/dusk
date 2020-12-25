package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * Sends a client script to run
 * @param id The client script id
 * @param params Additional parameters to run the script with (strings & integers only)
 */
data class ScriptMessage(val id : Int, val params : List<Any>) : GameMessage {
	constructor(id : Int, vararg params : Any) : this(id, params.toList())
}