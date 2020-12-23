package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameServiceMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 23, 2020
 */
data class DrawWindowPaneMessage(val id: Int, val type: Int)  : GameServiceMessage