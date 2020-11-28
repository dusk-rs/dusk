package rs.dusk.engine.entity.character.combat.strategy

import rs.dusk.engine.entity.character.combat.AttackStrategy
import rs.dusk.engine.entity.character.combat.CombatEvent
import rs.dusk.engine.entity.character.combat.CombatStrategy
import rs.dusk.engine.entity.character.combat.DamageStrategy
import rs.dusk.engine.entity.character.combat.events.MeleeDamageEvent

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 10:05 PM
 **/
class MeleeStrategy(private val damageStrategy: DamageStrategy, private val attackStrategy: AttackStrategy) : CombatStrategy {

    override fun createDamageEvent(): List<CombatEvent> {
        return listOf(MeleeDamageEvent(damageStrategy.nextHit()))
    }

    override fun nextAttackTime(): Long {
        return attackStrategy.nextAttackSpeed()
    }
}