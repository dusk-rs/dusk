package rs.dusk.engine.entity.item

/**
 * What should happen to an item when a player dies with it?
 */
enum class ItemKept {
    /**
     * Dropped onto the floor
     */
    Never,
    /**
     * If dropped, it vanishes and has to be reclaimed elsewhere
     */
    Reclaim,
    /**
     * Kept safe unless in the wilderness where it is dropped
     */
    Wilderness,
    /**
     * Always saved regardless
     */
    Always,
    /**
     * Disappears regardless of if kept on death
     */
    Vanish
}