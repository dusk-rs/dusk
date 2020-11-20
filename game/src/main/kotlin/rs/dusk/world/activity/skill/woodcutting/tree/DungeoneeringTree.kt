package rs.dusk.world.activity.skill.woodcutting.tree

import rs.dusk.world.activity.skill.woodcutting.log.DungeoneeringBranch

@Suppress("EnumEntryName")
enum class DungeoneeringTree(
    override val log: DungeoneeringBranch,
    override val level: Int,
    override val xp: Double,
    override val depleteRate: Double,
    override val chance: IntRange,
    override val lowDifference: IntRange,
    override val highDifference: IntRange,
    override val respawnDelay: IntRange
) : Tree {
    Tangle_Gum_Tree(DungeoneeringBranch.Tangle_Gum_Branches, 1, 35.0, 0.125, 0..0, 0..0, 0..0, -1..-1),
    Seeping_Elm_Tree(DungeoneeringBranch.Seeping_Elm_Branches, 10, 60.0, 0.125, 0..0, 0..0, 0..0, -1..-1),
    Blood_Spindle_Tree(DungeoneeringBranch.Blood_Spindle_Branches, 20, 85.0, 0.125, 0..0, 0..0, 0..0, -1..-1),
    Utuku_Tree(DungeoneeringBranch.Utuku_Branches, 30, 115.0, 0.125, 0..0, 0..0, 0..0, -1..-1),
    Spinebeam_Tree(DungeoneeringBranch.Spinebeam_Branches, 40, 145.0, 0.125, 0..0, 0..0, 0..0, -1..-1),
    Bovistrangler_Tree(DungeoneeringBranch.Bovistrangler_Branches, 50, 175.0, 0.125, 0..0, 0..0, 0..0, -1..-1),
    Thigat_Tree(DungeoneeringBranch.Thigat_Branches, 60, 210.0, 0.125, 0..0, 0..0, 0..0, -1..-1),
    Corpsethorn_Tree(DungeoneeringBranch.Corpsethorn_Branches, 70, 245.0, 0.125, 0..0, 0..0, 0..0, -1..-1),
    Entgallow_Tree(DungeoneeringBranch.Entgallow_Branches, 80, 285.0, 0.125, 0..0, 0..0, 0..0, -1..-1),
    Grave_creeper_Tree(DungeoneeringBranch.Grave_creeper_Branches, 90, 330.0, 0.125, 0..0, 0..0, 0..0, -1..-1);

    override val id: String = name.toLowerCase()
}