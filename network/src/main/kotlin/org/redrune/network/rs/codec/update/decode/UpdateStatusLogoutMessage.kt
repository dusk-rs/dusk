package org.redrune.network.rs.codec.update.decode

import org.redrune.core.network.model.message.Message

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
data class UpdateStatusLogoutMessage(val value: Int) : Message