package rs.dusk.network.codec.login.encode.message

import rs.dusk.network.codec.login.LoginMessage

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
data class LobbyConfigurationMessage(val username: String, val lastIpAddress: String, val lastLogin: Long) :
	LoginMessage