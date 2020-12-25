package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * Client variable; also known as "GlobalString"
 * @param id The config id
 * @param value The value to pass to the config
 */
data class VarcStrMessage(val id : Int, val value : String) : GameMessage