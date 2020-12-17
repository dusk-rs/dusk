package rs.dusk.network.codec.handshake.decode

import rs.dusk.core.network.packet.PacketMetaData
import rs.dusk.core.network.packet.access.PacketReader
import rs.dusk.network.codec.handshake.HandshakeMessageDecoder
import rs.dusk.network.codec.handshake.HandshakeOpcodes.GAME_CONNECTION
import rs.dusk.network.codec.handshake.decode.message.GameServiceRequest

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
@PacketMetaData(opcodes = [GAME_CONNECTION], length = 0)
class GameServiceRequestDecoder : HandshakeMessageDecoder<GameServiceRequest>() {
	
	override fun decode(packet : PacketReader) : GameServiceRequest {
		return GameServiceRequest()
	}
	
}