package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * Update the text of a interface component
 * @param id The id of the parent interface
 * @param component The index of the component
 * @param text The text to send
 */
data class InterfaceTextMessage(val id: Int, val component: Int, val text: String) : GameMessage