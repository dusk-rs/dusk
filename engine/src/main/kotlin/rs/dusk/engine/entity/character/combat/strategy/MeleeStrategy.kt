package rs.dusk.engine.entity.character.combat.strategy

import rs.dusk.engine.entity.character.combat.CombatEvent
import rs.dusk.engine.entity.character.combat.CombatStrategy
import rs.dusk.engine.entity.character.combat.events.NoTypeDamageEvent

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 10:05 PM
 **/
class MeleeStrategy : CombatStrategy {
    override fun createDamageEvent(): List<CombatEvent> {
        return listOf(NoTypeDamageEvent())
    }

    override fun nextAttackTime(): Long {
        return 1000
    }
}