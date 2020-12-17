package rs.dusk.network.codec.game.decode

import rs.dusk.core.network.packet.PacketMetaData
import rs.dusk.core.network.packet.access.PacketReader
import rs.dusk.network.codec.game.GameMessageDecoder
import rs.dusk.network.codec.game.decode.message.WorldListRefreshMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.REFRESH_WORLDS

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 19, 2020
 */
@PacketMetaData(opcodes = [REFRESH_WORLDS], length = 4)
class WorldListRefreshMessageDecoder : GameMessageDecoder<WorldListRefreshMessage>() {

    override fun decode(packet: PacketReader) = WorldListRefreshMessage(packet.readInt())

}