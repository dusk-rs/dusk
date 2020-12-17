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
	
	init {
		register()
		logger.debug { "Download Codec: ${repository.generateStatistics()}" }
	}
	
	override fun register() {
		repository.bindDecoders<DownloadServiceMessageDecoder<*>>()
		repository.bindHandlers<DownloadServiceMessageHandler<*>>()
		repository.bindEncoders<DownloadServiceMessageEncoder<*>>()
	}
	
	companion object {
		private val logger = InlineLogger()
	}
}

interface DownloadServiceMessage : Message

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since February 18, 2020
 */
abstract class DownloadServiceMessageDecoder<M : DownloadServiceMessage> : MessageDecoder<M>()

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since February 18, 2020
 */
abstract class DownloadServiceMessageEncoder<M : DownloadServiceMessage> : MessageEncoder<M>()

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since February 18, 2020
 */
abstract class DownloadServiceMessageHandler<M : DownloadServiceMessage> : MessageHandler<M>()

val downloadCodecModule = module {
	single(createdAtStart = true) { DownloadCodec() }
}