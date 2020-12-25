package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * Closes a client interface
 * @param id The id of the parent interface
 * @param component The index of the component to close
 */
data class InterfaceCloseMessage(val id: Int, val component: Int) : GameMessage