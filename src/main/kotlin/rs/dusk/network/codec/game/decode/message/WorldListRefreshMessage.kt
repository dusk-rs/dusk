package rs.dusk.network.codec.game.decode.message

import rs.dusk.network.codec.game.GameServiceMessage
import rs.dusk.network.codec.game.MessageCompanion

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 19, 2020
 */
data class WorldListRefreshMessage(val crc: Int) : GameServiceMessage {
    companion object : MessageCompanion<WorldListRefreshMessage>()
}