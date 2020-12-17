package rs.dusk.network.codec.download.decode

import rs.dusk.core.network.packet.PacketMetaData
import rs.dusk.core.network.packet.access.PacketReader
import rs.dusk.network.codec.download.DownloadServiceMessageDecoder
import rs.dusk.network.codec.download.DownloadServiceRequestOpcodes.STATUS_LOGGED_IN
import rs.dusk.network.codec.download.decode.message.DownloadLogin

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
@PacketMetaData(opcodes = [STATUS_LOGGED_IN], length = 3)
class DownloadLoginDecoder : DownloadServiceMessageDecoder<DownloadLogin>() {
	
	override fun decode(packet : PacketReader) : DownloadLogin {
		val value = packet.readMedium()
		return DownloadLogin(value)
	}
	
}