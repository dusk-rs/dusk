package rs.dusk.network.codec.login.encode.message

import rs.dusk.network.codec.login.LoginMessage

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 18, 2020
 */
data class GameLoginDetails(val rights: Int, val clientIndex: Int, val displayName: String) : LoginMessage