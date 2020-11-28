package rs.dusk.engine.entity.character.combat.strategy.damage

import rs.dusk.engine.entity.character.combat.DamageStrategy
import kotlin.random.Random

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 11:19 PM
 **/
class DefaultDamageStrategy : DamageStrategy {
    override fun calculateMaxDamage(): Int {
        return 50
    }

    override fun calculateMinDamage(): Int {
        return 1
    }

    override fun nextHit(): Int {
        return Random.nextInt(calculateMinDamage(), calculateMaxDamage())
    }
}