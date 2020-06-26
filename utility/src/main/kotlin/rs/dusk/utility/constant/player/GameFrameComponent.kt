package rs.dusk.utility.constant.player

import rs.dusk.utility.constant.player.InterfaceConstants.AncientSpellbook
import rs.dusk.utility.constant.player.InterfaceConstants.AreaStatusIcon
import rs.dusk.utility.constant.player.InterfaceConstants.ChatBackground
import rs.dusk.utility.constant.player.InterfaceConstants.ChatBox
import rs.dusk.utility.constant.player.InterfaceConstants.ClanChat
import rs.dusk.utility.constant.player.InterfaceConstants.CombatStyles
import rs.dusk.utility.constant.player.InterfaceConstants.DungeoneeringSpellbook
import rs.dusk.utility.constant.player.InterfaceConstants.Emotes
import rs.dusk.utility.constant.player.InterfaceConstants.EnergyOrb
import rs.dusk.utility.constant.player.InterfaceConstants.FilterButtons
import rs.dusk.utility.constant.player.InterfaceConstants.FriendsChat
import rs.dusk.utility.constant.player.InterfaceConstants.FriendsList
import rs.dusk.utility.constant.player.InterfaceConstants.HealthOrb
import rs.dusk.utility.constant.player.InterfaceConstants.Inventory
import rs.dusk.utility.constant.player.InterfaceConstants.Logout
import rs.dusk.utility.constant.player.InterfaceConstants.LunarSpellbook
import rs.dusk.utility.constant.player.InterfaceConstants.ModernSpellbook
import rs.dusk.utility.constant.player.InterfaceConstants.MusicPlayer
import rs.dusk.utility.constant.player.InterfaceConstants.Notes
import rs.dusk.utility.constant.player.InterfaceConstants.Options
import rs.dusk.utility.constant.player.InterfaceConstants.PrayerList
import rs.dusk.utility.constant.player.InterfaceConstants.PrayerOrb
import rs.dusk.utility.constant.player.InterfaceConstants.PrivateChat
import rs.dusk.utility.constant.player.InterfaceConstants.QuestJournals
import rs.dusk.utility.constant.player.InterfaceConstants.Stats
import rs.dusk.utility.constant.player.InterfaceConstants.SummoningOrb
import rs.dusk.utility.constant.player.InterfaceConstants.TaskSystem
import rs.dusk.utility.constant.player.InterfaceConstants.TradeSide
import rs.dusk.utility.constant.player.InterfaceConstants.WornEquipment

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since June 16, 2020
 */
enum class GameFrameComponent(

    /**
     * The index the component uses on fixed mode
     */
    val fixedIndex: Int,

    /**
     * The index the component uses on resizable mode
     */
    val resizableIndex: Int,

    /**
     * The interface ids that may possibly apply to this component
     */
    vararg val interfaceIds: Int
) {

    // Chat box
    CHAT_BOX(192, 73, ChatBox),
    CHAT_SETTINGS(68, 19, FilterButtons),
    PRIVATE_CHAT(17, 72, PrivateChat),

    // Minimap
    ENERGY_ORB(186, 179, EnergyOrb),
    HEALTH_ORB(183, 177, HealthOrb),
    PRAYER_ORB(185, 178, PrayerOrb),
    SUMMONING_ORB(188, 180, SummoningOrb),

    // Tab slots
    CLAN_CHAT(215, 101, ClanChat),
    COMBAT_STYLES(204, 90, CombatStyles),
    EMOTES(217, 103, Emotes),
    FRIENDS_CHAT(214, 100, FriendsChat),
    FRIENDS_LIST(213, 99, FriendsList),
    INVENTORY(208, 94, Inventory, TradeSide),
    LOGOUT(222, 108, Logout),
    SPELLBOOK(211, 97, ModernSpellbook, LunarSpellbook, AncientSpellbook, DungeoneeringSpellbook),
    MUSIC_PLAYER(218, 104, MusicPlayer),
    NOTES(219, 105, Notes),
    OPTIONS(216, 102, Options),
    PRAYER_LIST(210, 96, PrayerList),
    QUEST_JOURNALS(207, 93, QuestJournals),
    STATS(206, 92, Stats),
    TASK_SYSTEM(205, 91, TaskSystem),
    WORN_EQUIPMENT(209, 95, WornEquipment),

    // Overlays
    AREA(15, 15, AreaStatusIcon),
    OVERLAY(9, 12),
    SKILL_CREATION(4, 4);

    companion object {

        fun getComponentId(interfaceId: Int, resizable: Boolean) : Int? {
            for (component in values()) {
                for (componentInterfaceId in component.interfaceIds) {
                    if (componentInterfaceId == interfaceId) {
                        return (if (resizable) component.resizableIndex else component.fixedIndex)
                    }
                }
            }
            return null
        }
    }
}