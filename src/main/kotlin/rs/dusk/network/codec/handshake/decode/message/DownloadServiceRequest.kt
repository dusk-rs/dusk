package rs.dusk.network.codec.handshake.decode.message

import rs.dusk.network.codec.handshake.HandshakeMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class DownloadServiceRequest(val major : Int) : HandshakeMessage