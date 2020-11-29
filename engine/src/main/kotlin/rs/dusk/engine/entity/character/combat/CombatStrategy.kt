package rs.dusk.engine.entity.character.combat

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 10:04 PM
 * Handles creation of combat events and how ofter events like damage is sent
 **/
interface CombatStrategy {

    fun createDamageEvent() : List<CombatEvent>

    fun nextAttackTime(): Long

}