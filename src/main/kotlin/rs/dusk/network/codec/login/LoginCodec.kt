package rs.dusk.network.codec.login

import com.github.michaelbull.logging.InlineLogger
import org.koin.dsl.module
import rs.dusk.core.network.codec.Codec
import rs.dusk.core.network.message.Message
import rs.dusk.core.network.message.MessageDecoder
import rs.dusk.core.network.message.MessageEncoder
import rs.dusk.core.network.message.MessageHandler

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
class LoginCodec : Codec() {
	
	init {
		register()
		logger.debug { "Handshake Codec: ${repository.generateStatistics()}" }
	}
	
	override fun register() {
		repository.bindDecoders<LoginMessageDecoder<*>>()
		repository.bindHandlers<LoginMessageHandler<*>>()
		repository.bindEncoders<LoginMessageEncoder<*>>()
	}
	
	companion object {
		private val logger = InlineLogger()
	}
}

val loginCodecModule = module {
	single(createdAtStart = true) { LoginCodec() }
}

interface LoginMessage : Message

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class LoginMessageDecoder<M : LoginMessage> : MessageDecoder<M>()

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class LoginMessageHandler<M : LoginMessage> : MessageHandler<M>()

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class LoginMessageEncoder<M : LoginMessage> : MessageEncoder<M>()
