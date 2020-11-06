package rs.dusk.network.rs.codec.game.decode

import rs.dusk.core.network.codec.packet.access.PacketReader
import rs.dusk.core.network.model.packet.PacketMetaData
import rs.dusk.network.rs.codec.game.GameMessageDecoder
import rs.dusk.network.rs.codec.game.GameOpcodes.ONLINE_STATUS
import rs.dusk.network.rs.codec.game.decode.message.ChatFilterUpdateMessage

@PacketMetaData(opcodes = [ONLINE_STATUS], length = 3)
class ChatFilterMessageDecoder : GameMessageDecoder<ChatFilterUpdateMessage>() {

    override fun decode(packet: PacketReader) =
        ChatFilterUpdateMessage(packet.readByte(), packet.readByte(), packet.readByte())

}