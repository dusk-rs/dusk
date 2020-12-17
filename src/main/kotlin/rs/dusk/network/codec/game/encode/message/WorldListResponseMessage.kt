package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameServiceMessage

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 22, 2020
 */
data class WorldListResponseMessage(val full : Boolean) : GameServiceMessage