package rs.dusk.network.rs.codec.login.encode.message

import rs.dusk.core.network.model.message.Message

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since February 18, 2020
 */
data class LobbyLoginConnectionResponseMessage(val opcode: Int) : Message