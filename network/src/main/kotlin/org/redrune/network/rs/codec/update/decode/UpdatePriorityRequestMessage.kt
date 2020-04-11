package org.redrune.network.rs.codec.update.decode

import org.redrune.core.network.model.message.Message

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
data class UpdatePriorityRequestMessage(val indexId: Int, val archiveId: Int) : Message {
    constructor(hash: Int) : this(hash shr 16, hash and 0xffff)
}