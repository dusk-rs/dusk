package rs.dusk.network.codec.download.decode.message

import rs.dusk.network.codec.download.DownloadServiceMessage

/**
 * This message is sent when the connection is stable for download to start
 *
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
data class DownloadConnected(val value : Int) : DownloadServiceMessage