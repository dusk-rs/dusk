package rs.dusk.engine.entity.character.combat.events

import rs.dusk.engine.entity.character.Character
import rs.dusk.engine.entity.character.combat.CombatEvent

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 9:09 PM
 **/
class NoTypeDamageEvent : CombatEvent {
    override fun apply(character: Character) {

        //reduce character hitpoints
        //calculated reduced

        println("Ouch target got hit. $character")

    }
}