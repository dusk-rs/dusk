package rs.dusk.network.rs.codec.game

import rs.dusk.core.network.codec.Codec
import rs.dusk.core.network.codec.message.MessageDecoder
import rs.dusk.core.network.codec.message.MessageEncoder
import rs.dusk.core.network.codec.message.MessageHandler
import rs.dusk.core.network.model.message.Message

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since February 18, 2020
 */
class GameCodec : Codec() {

    override fun register() {
        bindDecoders<GameMessageDecoder<*>>()
        bindHandlers<GameMessageHandler<*>>()
        bindEncoders<GameMessageEncoder<*>>()
    }
}

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since February 18, 2020
 */
abstract class GameMessageEncoder<M : Message> : MessageEncoder<M>()

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since February 18, 2020
 */
abstract class GameMessageDecoder<M : Message> : MessageDecoder<M>()

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since February 18, 2020
 */
abstract class GameMessageHandler<M : Message> : MessageHandler<M>()