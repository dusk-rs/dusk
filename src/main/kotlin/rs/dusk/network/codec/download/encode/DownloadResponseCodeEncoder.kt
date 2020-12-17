package rs.dusk.network.codec.download.encode

import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.download.DownloadServiceMessageEncoder
import rs.dusk.network.codec.download.encode.message.DownloadResponseCode

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class DownloadResponseCodeEncoder : DownloadServiceMessageEncoder<DownloadResponseCode>() {
	
	override fun encode(builder : PacketWriter, msg : DownloadResponseCode) {
		builder.writeOpcode(msg.value, PacketType.FIXED)
	}
}