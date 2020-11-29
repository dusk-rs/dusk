package rs.dusk.engine.entity.character.combat

import rs.dusk.engine.entity.character.Character

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 8:55 PM
 **/
interface CombatEvent {

    /**
     * Applies given event to target
     * @param character the target
     */

    fun apply(character: Character)

}