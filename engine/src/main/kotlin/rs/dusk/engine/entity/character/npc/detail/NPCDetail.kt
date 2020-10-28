package rs.dusk.engine.entity.character.npc.detail

import rs.dusk.engine.entity.EntityDetail

data class NPCDetail(
    val id: Int,
    val members: Boolean,
    val weakness: String = "",
    val level: Int,
    val lifepoints: Int,
    val defence: Int,
    val attack: Int,
    val magic: Int,
    val ranged: Int,
    val xp: String,
    val slayerlevel: Int,
    val slayercat: String = "",
    val size: Int,
    val attackable: Boolean,
    val aggressive: Boolean,
    val poisonous: Boolean,
    val description: String,
    val area: Array<String> = emptyArray(),
    val animations: Map<String, Int>
) : EntityDetail
{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NPCDetail

        if (id != other.id) return false
        if (members != other.members) return false
        if (weakness != other.weakness) return false
        if (level != other.level) return false
        if (lifepoints != other.lifepoints) return false
        if (defence != other.defence) return false
        if (attack != other.attack) return false
        if (magic != other.magic) return false
        if (ranged != other.ranged) return false
        if (xp != other.xp) return false
        if (slayerlevel != other.slayerlevel) return false
        if (slayercat != other.slayercat) return false
        if (size != other.size) return false
        if (attackable != other.attackable) return false
        if (aggressive != other.aggressive) return false
        if (poisonous != other.poisonous) return false
        if (description != other.description) return false
        if (!area.contentEquals(other.area)) return false
        if (animations != other.animations) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + members.hashCode()
        result = 31 * result + weakness.hashCode()
        result = 31 * result + level
        result = 31 * result + lifepoints
        result = 31 * result + defence
        result = 31 * result + attack
        result = 31 * result + magic
        result = 31 * result + ranged
        result = 31 * result + xp.hashCode()
        result = 31 * result + slayerlevel
        result = 31 * result + slayercat.hashCode()
        result = 31 * result + size
        result = 31 * result + attackable.hashCode()
        result = 31 * result + aggressive.hashCode()
        result = 31 * result + poisonous.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + area.contentHashCode()
        result = 31 * result + animations.hashCode()
        return result
    }

}