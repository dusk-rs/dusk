package rs.dusk.network.codec.download

import com.github.michaelbull.logging.InlineLogger
import org.koin.dsl.module
import rs.dusk.core.network.codec.Codec
import rs.dusk.core.network.message.Message
import rs.dusk.core.network.message.MessageDecoder
import rs.dusk.core.network.message.MessageEncoder
import rs.dusk.core.network.message.MessageHandler

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class DownloadCodec : Codec() {
	
	private val logger = InlineLogger()
	
	init {
		register()
		logger.debug { "Download Codec: ${repository.generateStatistics()}" }
	}
	
	override fun register() {
		repository.bindDecoders<DownloadMessageDecoder<*>>()
		repository.bindHandlers<DownloadMessageHandler<*>>()
		repository.bindEncoders<DownloadMessageEncoder<*>>()
	}
}

interface DownloadServiceMessage : Message

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class DownloadMessageDecoder<M : DownloadServiceMessage> : MessageDecoder<M>()

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class DownloadMessageEncoder<M : DownloadServiceMessage> : MessageEncoder<M>()

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class DownloadMessageHandler<M : DownloadServiceMessage> : MessageHandler<M>()

val downloadCodecModule = module {
	single(createdAtStart = true) { DownloadCodec() }
}