package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * Sends command to display the players head on a interface component
 * @param id The id of the parent interface
 * @param component The index of the component
 */
data class InterfaceHeadPlayerMessage(val id : Int, val component : Int) : GameMessage