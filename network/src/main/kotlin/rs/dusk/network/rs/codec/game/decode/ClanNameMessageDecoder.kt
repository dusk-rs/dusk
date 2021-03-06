package rs.dusk.network.rs.codec.game.decode

import rs.dusk.core.network.codec.packet.access.PacketReader
import rs.dusk.core.network.model.packet.PacketMetaData
import rs.dusk.core.network.model.packet.PacketType.Companion.VARIABLE_LENGTH_BYTE
import rs.dusk.network.rs.codec.game.GameMessageDecoder
import rs.dusk.network.rs.codec.game.GameOpcodes.CLAN_NAME
import rs.dusk.network.rs.codec.game.decode.message.ClanNameMessage

@PacketMetaData(opcodes = [CLAN_NAME], length = VARIABLE_LENGTH_BYTE)
class ClanNameMessageDecoder : GameMessageDecoder<ClanNameMessage>() {

    override fun decode(packet: PacketReader) = ClanNameMessage(packet.readString())

}