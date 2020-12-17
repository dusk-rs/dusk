package rs.dusk.network.codec.handshake.decode

import rs.dusk.core.network.packet.PacketMetaData
import rs.dusk.core.network.packet.access.PacketReader
import rs.dusk.network.codec.handshake.HandshakeMessageDecoder
import rs.dusk.network.codec.handshake.HandshakeOpcodes.DOWNLOAD_SERVICE
import rs.dusk.network.codec.handshake.decode.message.DownloadServiceRequest

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
@PacketMetaData(opcodes = [DOWNLOAD_SERVICE], length = 4)
class DownloadServiceRequestDecoder : HandshakeMessageDecoder<DownloadServiceRequest>() {
	
	override fun decode(packet : PacketReader) : DownloadServiceRequest {
		val major = packet.readInt()
		return DownloadServiceRequest(major)
	}
	
}
