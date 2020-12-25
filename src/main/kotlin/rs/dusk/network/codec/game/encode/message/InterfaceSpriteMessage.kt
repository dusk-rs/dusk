package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * Sends a sprite to a interface component
 * @param id The id of the parent interface
 * @param component The index of the component
 * @param sprite The sprite id
 */
data class InterfaceSpriteMessage(val id : Int, val component : Int, val sprite : Int) : GameMessage