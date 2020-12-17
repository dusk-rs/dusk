package rs.dusk.network.codec.download.decode

import rs.dusk.core.network.packet.PacketMetaData
import rs.dusk.core.network.packet.access.PacketReader
import rs.dusk.network.codec.download.DownloadServiceMessageDecoder
import rs.dusk.network.codec.download.DownloadServiceRequestOpcodes.CONNECTED
import rs.dusk.network.codec.download.decode.message.DownloadConnected

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
@PacketMetaData(opcodes = [CONNECTED], length = 3)
class DownloadConnectedDecoder : DownloadServiceMessageDecoder<DownloadConnected>() {
	
	override fun decode(packet : PacketReader) : DownloadConnected {
		val value = packet.readMedium()
		return DownloadConnected(value)
	}
	
}