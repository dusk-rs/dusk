package rs.dusk.network.codec.download.decode

import rs.dusk.core.network.buffer.DataType
import rs.dusk.core.network.packet.PacketMetaData
import rs.dusk.core.network.packet.access.PacketReader
import rs.dusk.network.codec.download.DownloadServiceMessageDecoder
import rs.dusk.network.codec.download.DownloadServiceRequestOpcodes.FILE_REQUEST
import rs.dusk.network.codec.download.DownloadServiceRequestOpcodes.PRIORITY_FILE_REQUEST
import rs.dusk.network.codec.download.decode.message.DownloadRequest

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
@PacketMetaData(opcodes = [FILE_REQUEST, PRIORITY_FILE_REQUEST], length = 3)
class DownloadRequestDecoder : DownloadServiceMessageDecoder<DownloadRequest>() {
	
	override fun decode(packet: PacketReader): DownloadRequest {
		val hash = packet.readUnsigned(DataType.MEDIUM)
		val indexId = (hash shr 16).toInt()
		val archiveId = (hash and 0xffff).toInt()
		return DownloadRequest(indexId, archiveId, packet.opcode == PRIORITY_FILE_REQUEST)
	}
	
}