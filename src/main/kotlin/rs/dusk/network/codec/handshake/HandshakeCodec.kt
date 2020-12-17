package rs.dusk.network.codec.handshake

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
class HandshakeCodec : Codec() {
	
	private val logger = InlineLogger()
	
	init {
		register()
		logger.debug { "Handshake Codec: ${repository.generateStatistics()}" }
	}
	
	override fun register() {
		repository.bindDecoders<HandshakeMessageDecoder<*>>()
		repository.bindHandlers<HandshakeMessageHandler<*>>()
		repository.bindEncoders<HandshakeMessageEncoder<*>>()
	}
	
}


interface HandshakeMessage : Message

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class HandshakeMessageHandler<M : HandshakeMessage> : MessageHandler<M>()

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class HandshakeMessageEncoder<M : HandshakeMessage> : MessageEncoder<M>()

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class HandshakeMessageDecoder<M : HandshakeMessage> : MessageDecoder<M>()

val handshakeCodecModule = module {
	single(createdAtStart = true) { HandshakeCodec() }
}