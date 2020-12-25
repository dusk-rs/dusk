package rs.dusk.engine.entity.character.update.visual.npc

import rs.dusk.game.entity.character.update.Visual
import rs.dusk.game.entity.character.npc.NPC

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
data class CombatLevel(var level: Int = 1) : Visual

const val COMBAT_LEVEL_MASK = 0x80000

fun NPC.flagCombatLevel() = visuals.flag(COMBAT_LEVEL_MASK)

fun NPC.getCombatLevel() = visuals.getOrPut(COMBAT_LEVEL_MASK) { CombatLevel() }

var NPC.combatLevel: Int
    get() = getCombatLevel().level
    set(value) {
        getCombatLevel().level = value
        flagCombatLevel()
    }