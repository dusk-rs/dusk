package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * Sends an animation to a interface component
 * @param id The id of the parent interface
 * @param component The index of the component
 * @param animation The animation id
 */
data class InterfaceAnimationMessage(val id: Int, val component: Int, val animation: Int) : GameMessage