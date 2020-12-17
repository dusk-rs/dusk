package rs.dusk.cache.definition

import rs.dusk.core.network.buffer.read.Reader

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @author Tyluur <itstyluur@gmail.com>
 * @since April 08, 2020
 */
interface Recolourable {
	var originalColours : ShortArray?
	var modifiedColours : ShortArray?
	var originalTextureColours : ShortArray?
	var modifiedTextureColours : ShortArray?
	
	fun readColours(buffer : Reader) {
		val length = buffer.readUnsignedByte()
		originalColours = ShortArray(length)
		modifiedColours = ShortArray(length)
		repeat(length) { count ->
			originalColours!![count] = buffer.readShort().toShort()
			modifiedColours!![count] = buffer.readShort().toShort()
		}
	}
	
	fun readTextures(buffer : Reader) {
		val length = buffer.readUnsignedByte()
		originalTextureColours = ShortArray(length)
		modifiedTextureColours = ShortArray(length)
		repeat(length) { count ->
			originalTextureColours!![count] = buffer.readShort().toShort()
			modifiedTextureColours!![count] = buffer.readShort().toShort()
		}
	}
}