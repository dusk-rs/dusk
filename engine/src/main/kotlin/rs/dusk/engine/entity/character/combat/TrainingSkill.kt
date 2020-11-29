package rs.dusk.engine.entity.character.combat

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 11:38 PM
 * Experience types when attacking.
 * Shared Melee experience will provide the player with attack, strength and defence
 * Shared Magic experience will provide the player with magic and defence
 * Shared Ranged experience will provide the player with ranged and defence
 **/
enum class TrainingSkill {

    ATTACK,
    STRENGTH,
    DEFENCE,
    RANGED,
    MAGIC,
    SHARED_MELEE,
    SHARED_MAGIC,
    SHARED_RANGED

}