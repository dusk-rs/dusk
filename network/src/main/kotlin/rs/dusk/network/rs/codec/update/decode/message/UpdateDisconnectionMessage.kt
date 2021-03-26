package rs.dusk.network.rs.codec.update.decode.message

import rs.dusk.core.network.model.message.Message

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since February 18, 2020
 */
data class UpdateDisconnectionMessage(val value: Int) : Message