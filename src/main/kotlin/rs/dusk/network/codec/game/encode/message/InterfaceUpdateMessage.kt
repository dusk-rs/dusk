package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 18, 2020
 */
data class InterfaceUpdateMessage(val id : Int, val type : Int) : GameMessage