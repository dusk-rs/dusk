package rs.dusk.engine.model.entity.index.player.data.interfaces

/**
 * This class represents an interface that is displayed to a player.
 *
 * @author Tyluur <contact@kiaira.tech>
 *
 * @param interfaceId The id of the interface that is shown
 * @param index The index of the parent interface which this interface was drawn
 *
 * @since June 25, 2020
 */
data class InterfaceDisplayed(val interfaceId: Int, val index: Int)