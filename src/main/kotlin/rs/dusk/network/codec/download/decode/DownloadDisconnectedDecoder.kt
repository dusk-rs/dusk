package rs.dusk.network.codec.download.decode

import rs.dusk.core.network.packet.PacketMetaData
import rs.dusk.core.network.packet.access.PacketReader
import rs.dusk.network.codec.download.DownloadServiceMessageDecoder
import rs.dusk.network.codec.download.DownloadServiceRequestOpcodes.DISCONNECTED
import rs.dusk.network.codec.download.decode.message.DownloadDisconnected

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
@PacketMetaData(opcodes = [DISCONNECTED], length = 3)
class DownloadDisconnectedDecoder : DownloadServiceMessageDecoder<DownloadDisconnected>() {
	
	override fun decode(packet: PacketReader): DownloadDisconnected {
		val value = packet.readMedium()
		return DownloadDisconnected(value)
	}
	
}