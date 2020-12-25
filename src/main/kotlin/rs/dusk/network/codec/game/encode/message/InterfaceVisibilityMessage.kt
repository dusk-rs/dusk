package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * Toggles a interface component
 * @param id The parent interface id
 * @param component The component to change
 * @param hide Visibility
 */
data class InterfaceVisibilityMessage(val id : Int, val component : Int, val hide : Boolean) : GameMessage