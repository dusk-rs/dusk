package rs.dusk.engine.entity.character.combat.events

import rs.dusk.engine.entity.character.Character
import rs.dusk.engine.entity.character.combat.CombatEvent

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 9:09 PM
 **/
class MeleeDamageEvent(val dmg: Int) : CombatEvent {
    override fun apply(character: Character) {
        println("Melee damage $dmg $character")
    }
}