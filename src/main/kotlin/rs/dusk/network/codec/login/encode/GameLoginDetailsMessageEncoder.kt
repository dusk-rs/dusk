package rs.dusk.network.codec.login.encode

import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.login.LoginMessageEncoder
import rs.dusk.network.codec.login.encode.message.GameLoginDetails

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
class GameLoginDetailsMessageEncoder : LoginMessageEncoder<GameLoginDetails>() {

    override fun encode(builder: PacketWriter, msg: GameLoginDetails) {
        val (rights, clientIndex, displayName) = msg
        builder.apply {
            writeOpcode(2, PacketType.BYTE)
            writeByte(rights)
            writeByte(0)//Unknown - something to do with skipping chat messages
            writeByte(0)
            writeByte(0)
            writeByte(0)
            writeByte(0)//Moves chat box position
            writeShort(clientIndex)
            writeByte(true)
            writeMedium(0)
            writeByte(true)
            writeString(displayName)
        }
    }

}
