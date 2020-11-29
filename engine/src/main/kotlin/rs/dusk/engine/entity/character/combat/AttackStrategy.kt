package rs.dusk.engine.entity.character.combat

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 11:35 PM
 * eg stab, block, crush
 **/
interface AttackStrategy {

    /**
     * The experience in which this provides on damage
     */

    val training: TrainingSkill

    fun minAttackSpeed(): Long
    fun maxAttackSpeed(): Long

    /**
     * The next attack speed
     */

    fun nextAttackSpeed(): Long

}