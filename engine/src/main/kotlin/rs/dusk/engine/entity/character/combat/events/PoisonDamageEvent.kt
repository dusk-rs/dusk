package rs.dusk.engine.entity.character.combat.events

import rs.dusk.engine.entity.character.Character
import rs.dusk.engine.entity.character.combat.CombatEvent

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 11:14 PM
 **/
class PoisonDamageEvent : CombatEvent {
    override fun apply(character: Character) {
        println("Poisoning $character")
    }
}