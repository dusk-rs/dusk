package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 22, 2020
 */
data class WorldListResponseMessage(val full : Boolean) : GameMessage