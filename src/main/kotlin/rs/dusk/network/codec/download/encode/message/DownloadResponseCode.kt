package rs.dusk.network.codec.download.encode.message

import rs.dusk.network.codec.NetworkResponseCode
import rs.dusk.network.codec.download.DownloadServiceMessage

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
data class DownloadResponseCode(val value: Int) : DownloadServiceMessage {

    constructor(response: NetworkResponseCode) : this(response.opcode)

}