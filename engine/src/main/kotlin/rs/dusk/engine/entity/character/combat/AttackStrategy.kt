package rs.dusk.engine.entity.character.combat

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 11:35 PM
 * eg stab, block, crush
 **/
interface AttackStrategy {

    val training: TrainingSkill

    fun minAttackSpeed(): Long
    fun maxAttackSpeed(): Long

    fun nextAttackSpeed(): Long

}