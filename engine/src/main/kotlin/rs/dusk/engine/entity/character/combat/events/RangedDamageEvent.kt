package rs.dusk.engine.entity.character.combat.events

import rs.dusk.engine.entity.character.Character
import rs.dusk.engine.entity.character.combat.CombatEvent

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 11:13 PM
 **/
class RangedDamageEvent : CombatEvent {
    override fun apply(character: Character) {
        println("Apply range damage $character")
    }
}