package rs.dusk.engine.model.entity.index.player.data

/**
 * @author Tyluur <contact@kiaira.tech>
 *
 * @param displayMode The client mode the player is on, ranges from [0..3], with a value >= 2 implying a resizable game mode
 * @param clientWidth The width of the player's client
 * @param clientHeight The height of the player's client
 * @since June 25, 2020
 */
class ClientSettings(
    var displayMode: Int, var clientWidth: Int, var clientHeight: Int
) {

    /**
     * If the player is playing on a game mode that can be resized
     */
    fun isResizable() = displayMode >= 2
}