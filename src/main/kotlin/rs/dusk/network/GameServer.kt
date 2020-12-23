package rs.dusk.network

import com.github.michaelbull.logging.InlineLogger
import inject
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.koin.dsl.module
import rs.dusk.core.network.NetworkPipeline
import rs.dusk.core.network.NetworkServer
import rs.dusk.core.network.channel.event.ChannelEventChain
import rs.dusk.core.network.channel.event.ChannelEventListener
import rs.dusk.core.network.channel.event.ChannelEventType.EXCEPTION
import rs.dusk.core.network.channel.event.type.ChannelExceptionEvent
import rs.dusk.core.network.channel.setCodec
import rs.dusk.core.network.message.NetworkMessageReader
import rs.dusk.core.network.message.decode.OpcodeMessageDecoder
import rs.dusk.core.network.message.encode.GenericMessageEncoder
import rs.dusk.core.network.packet.decode.SimplePacketDecoder
import rs.dusk.core.network.session.Session
import rs.dusk.core.network.session.setSession
import rs.dusk.network.codec.handshake.HandshakeCodec

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @Since December 17, 2020
 */
class GameServer : NetworkServer() {
	
	private val handshakeCodec : HandshakeCodec by inject()
	
	/**
	 * The port to connect to
	 * #configuration
	 */
	private val port = 43594
	
	init {
		listen()
	}
	
	override fun listen() {
		val chain = ChannelEventChain().apply {
			append(EXCEPTION, ChannelExceptionEvent())
		}
		
		val pipeline = NetworkPipeline {
			val channel = it.channel()
			
			it.addLast("packet.decoder", SimplePacketDecoder())
			it.addLast("message.decoder", OpcodeMessageDecoder())
			it.addLast("message.reader", NetworkMessageReader())
			it.addLast("message.encoder", GenericMessageEncoder())
			it.addLast("channel.listener", ChannelEventListener(chain))
			it.addLast(LoggingHandler(LogLevel.DEBUG))
			
			channel.setCodec(handshakeCodec)
			channel.setSession(Session(channel))
		}
		configure(pipeline)
		start(port)
		logger.info { "Successfully bound to port $port" }
	}
	
	companion object {
		private val logger = InlineLogger()
	}
	
}

val gameServerModule = module {
	single(createdAtStart = true) { GameServer() }
}