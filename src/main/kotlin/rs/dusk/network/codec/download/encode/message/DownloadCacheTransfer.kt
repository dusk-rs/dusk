package rs.dusk.network.codec.download.encode.message

import rs.dusk.network.codec.download.DownloadServiceMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since February 18, 2020
 */
data class DownloadCacheTransfer(
	val indexId : Int,
	val archiveId : Int,
	val data : ByteArray,
	val compression : Int,
	val length : Int,
	val attributes : Int
) : DownloadServiceMessage {
	override fun equals(other : Any?) : Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false
		
		other as DownloadCacheTransfer
		
		if (indexId != other.indexId) return false
		if (archiveId != other.archiveId) return false
		if (!data.contentEquals(other.data)) return false
		if (compression != other.compression) return false
		if (length != other.length) return false
		if (attributes != other.attributes) return false
		
		return true
	}
	
	override fun hashCode() : Int {
		var result = indexId
		result = 31 * result + archiveId
		result = 31 * result + data.contentHashCode()
		result = 31 * result + compression
		result = 31 * result + length
		result = 31 * result + attributes
		return result
	}
}