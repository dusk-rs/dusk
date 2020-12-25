package rs.dusk.network.codec.game.encode

import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.ScriptMessage
import rs.dusk.network.rs.codec.game.GameOpcodes

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 25, 2020
 */
class ScriptMessageEncoder : GameMessageEncoder<ScriptMessage>() {
	
	override fun encode(builder: PacketWriter, msg: ScriptMessage) {
		val (id, params) = msg
		builder.apply {
			writeOpcode(GameOpcodes.SCRIPT, PacketType.SHORT)
			val types = StringBuilder()
			for (param in params) {
				types.append(if (param is String) "s" else "i")
			}
			writeString(types.toString())
			for (param in params.reversed()) {
				if (param is String) {
					writeString(param)
				} else if (param is Int) {
					writeInt(param)
				}
			}
			writeInt(id)
		}
	}
}