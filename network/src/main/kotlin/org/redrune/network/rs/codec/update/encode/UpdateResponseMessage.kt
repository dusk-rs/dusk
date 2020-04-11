package org.redrune.network.rs.codec.update.encode

import io.netty.buffer.Unpooled
import org.redrune.core.network.model.message.Message

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
data class UpdateResponseMessage(
    val data: ByteArray
) : Message {

    constructor(
        indexId: Int,
        archiveId: Int,
        data: ByteArray,
        compression: Int,
        length: Int,
        attributes: Int
    ) : this(
        encode(
            indexId,
            archiveId,
            attributes,
            length,
            compression,
            data
        )
    )

    companion object {
        fun encode(indexId: Int, archiveId: Int, attributes: Int, length: Int, compression: Int, data: ByteArray): ByteArray {
            val buffer = Unpooled.buffer()

            buffer.writeByte(indexId)
            buffer.writeShort(archiveId)
            buffer.writeByte(attributes)
            buffer.writeInt(length)

            val realLength = if (compression != 0) length + 4 else length
            for (offset in 5 until realLength + 5) {
                if (buffer.writerIndex() % 512 == 0) {
                    buffer.writeByte(255)
                }
                buffer.writeByte(data[offset].toInt())
            }
            return buffer.array()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpdateResponseMessage

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}