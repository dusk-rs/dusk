package rs.dusk.network.rs.codec.game.encode.message

import rs.dusk.core.network.model.message.Message

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since February 22, 2020
 */
data class WorldListResponseMessage(val full: Boolean) : Message