package rs.dusk.network.codec.download.encode

import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.download.DownloadServiceMessageEncoder
import rs.dusk.network.codec.download.DownloadServiceResponseCodes.JS5_RESPONSE_OK
import rs.dusk.network.codec.download.encode.message.DownloadServiceResponse

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class DownloadServiceResponseEncoder : DownloadServiceMessageEncoder<DownloadServiceResponse>() {
	
	override fun encode(builder : PacketWriter, msg : DownloadServiceResponse) {
		builder.writeByte(msg.response)
		if (msg.response == JS5_RESPONSE_OK) {
			GRAB_SERVER_KEYS.forEach {
				builder.writeInt(it)
			}
		}
	}
	
	companion object {
		
		/**
		 * The keys sent during grab server decoding
		 */
		private val GRAB_SERVER_KEYS = intArrayOf(
			1362,
			77448,
			44880,
			39771,
			24563,
			363672,
			44375,
			0,
			1614,
			0,
			5340,
			142976,
			741080,
			188204,
			358294,
			416732,
			828327,
			19517,
			22963,
			16769,
			1244,
			11976,
			10,
			15,
			119,
			817677,
			1624243
		)
	}
}