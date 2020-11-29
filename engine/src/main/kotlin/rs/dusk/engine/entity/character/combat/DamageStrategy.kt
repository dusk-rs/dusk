package rs.dusk.engine.entity.character.combat

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 11:17 PM
 * Handles calculations of incoming damage
 **/
interface DamageStrategy {

    fun calculateMaxDamage(): Int
    fun calculateMinDamage(): Int

    fun nextHit(): Int

}