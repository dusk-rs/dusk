package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since June 19, 2020
 * @param offset The tile offset from the [ChunkUpdateMessage] sent
 * @param id Item id
 * @param oldAmount Previous item stack size
 * @param newAmount Updated item stack size
 */
data class FloorItemUpdateMessage(
    val offset: Int,
    val id: Int,
    val oldAmount: Int,
    val newAmount: Int
) : GameMessage