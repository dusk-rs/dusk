package rs.dusk.network.codec.game

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
class GameCodec : Codec() {
	
	init {
		register()
		logger.debug { "Handshake Codec: ${repository.generateStatistics()}" }
	}
	
	override fun register() {
		repository.bindDecoders<GameMessageDecoder<*>>()
		repository.bindHandlers<GameMessageHandler<*>>()
		repository.bindEncoders<GameMessageEncoder<*>>()
	}
	
	companion object {
		private val logger = InlineLogger()
	}
}

val gameCodecModule = module {
	single(createdAtStart = true) { GameCodec() }
}

interface GameMessage : Message

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class GameMessageEncoder<M : GameMessage> : MessageEncoder<M>()

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class GameMessageDecoder<M : GameMessage> : MessageDecoder<M>()

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
abstract class GameMessageHandler<M : GameMessage> : MessageHandler<M>()