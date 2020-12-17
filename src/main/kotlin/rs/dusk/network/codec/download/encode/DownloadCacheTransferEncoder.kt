package rs.dusk.network.codec.download.encode

import io.netty.buffer.Unpooled
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.download.DownloadServiceMessageEncoder
import rs.dusk.network.codec.download.encode.message.DownloadCacheTransfer

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class DownloadCacheTransferEncoder : DownloadServiceMessageEncoder<DownloadCacheTransfer>() {
	
	@Suppress("DEPRECATED_IDENTITY_EQUALS")
	override fun encode(builder: PacketWriter, msg: DownloadCacheTransfer) {
		val (indexId, archiveId, data, compression, length, attributes) = msg
		
		val buffer = Unpooled.buffer()
		
		buffer.writeByte(indexId)
		buffer.writeShort(archiveId)
		buffer.writeByte(attributes)
		buffer.writeInt(length)
		
		val realLength = if (compression != 0) length + 4 else length
		for (offset in 5 until realLength + 5) {
			if (buffer.writerIndex() % 512 === 0) {
				buffer.writeByte(255)
			}
			buffer.writeByte(data[offset].toInt())
		}
		builder.writeBytes(buffer)
	}
	
}