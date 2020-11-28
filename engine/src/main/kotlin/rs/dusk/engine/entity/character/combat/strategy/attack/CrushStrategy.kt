package rs.dusk.engine.entity.character.combat.strategy.attack

import rs.dusk.engine.entity.character.combat.AttackStrategy
import rs.dusk.engine.entity.character.combat.TrainingSkill
import kotlin.random.Random

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 11:37 PM
 **/
class CrushStrategy : AttackStrategy {

    override val training: TrainingSkill
        get() = TrainingSkill.SHARED_MELEE

    override fun minAttackSpeed(): Long {
        return 1500
    }

    override fun maxAttackSpeed(): Long {
        return 2000
    }

    override fun nextAttackSpeed(): Long {
        return Random.nextLong(minAttackSpeed(), maxAttackSpeed())
    }
}