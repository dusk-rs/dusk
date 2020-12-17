package rs.dusk.network.codec.download.decode.message

import rs.dusk.network.codec.download.DownloadServiceMessage

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
data class DownloadRequest(val indexId: Int, val archiveId: Int, val priority: Boolean) : DownloadServiceMessage