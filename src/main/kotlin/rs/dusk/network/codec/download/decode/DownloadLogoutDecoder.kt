package rs.dusk.network.codec.download.decode

import rs.dusk.core.network.packet.PacketMetaData
import rs.dusk.core.network.packet.access.PacketReader
import rs.dusk.network.codec.download.DownloadServiceMessageDecoder
import rs.dusk.network.codec.download.DownloadServiceRequestOpcodes.STATUS_LOGGED_OUT
import rs.dusk.network.codec.download.decode.message.DownloadLogout

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
@PacketMetaData(opcodes = [STATUS_LOGGED_OUT], length = 3)
class DownloadLogoutDecoder : DownloadServiceMessageDecoder<DownloadLogout>() {
	
	override fun decode(packet : PacketReader) : DownloadLogout {
		val value = packet.readMedium()
		return DownloadLogout(value)
	}
	
}