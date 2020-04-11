package org.redrune.network.rs.codec.update.encode

import org.redrune.core.network.model.message.Message

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
data class UpdateVersionSuccessMessage(val opcode: Int) : Message