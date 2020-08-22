package rs.dusk.network.rs.codec.game

object GameOpcodes {

    /* Decode */

    const val OBJECT_OPTION_4 = 0
    const val JOIN_FRIEND_CHAT = 1
    const val OBJECT_OPTION_2 = 2
    const val ENTER_INTEGER = 3
    const val INTERFACE_OPTION_3 = 4
    const val MOVE_CAMERA = 5
    const val PING_REPLY = 6
    const val CLAN_NAME = 7
    const val REMOVE_FRIEND = 8
    const val NPC_OPTION_1 = 9
    const val INTERFACE_OPTION_8 = 10
    const val OBJECT_OPTION_1 = 11
    const val WALK = 12
    const val RESUME_PLAYER_OBJ_DIALOGUE = 13
    const val PLAYER_OPTION_1 = 14
    const val RECEIVE_COUNT = 15
    const val PING = 16
    const val ADD_IGNORE = 17
    const val INTERFACE_OPTION_7 = 18
    const val PLAYER_OPTION_10 = 19
    const val INTERFACE_OPTION_9 = 20
    const val INTERFACE_ON_FLOOR_ITEM = 21
    const val COLOUR_ID = 22
    const val CHAT_TYPE = 23
    const val FLOOR_ITEM_OPTION_3 = 24
    const val INTERFACE_OPTION_10 = 25
    const val SWITCH_INTERFACE_COMPONENTS = 26
    const val FLOOR_ITEM_OPTION_6 = 27
    const val NPC_OPTION_5 = 28
    const val MOVE_MOUSE = 29
    const val QUICK_PUBLIC_MESSAGE = 30
    const val NPC_OPTION_3 = 31
    const val KICK_FRIEND_CHAT = 32
    const val DONE_LOADING_REGION = 33
    const val REFRESH_WORLDS = 34
    const val FLOOR_ITEM_OPTION_5 = 35
    const val PUBLIC_MESSAGE = 36
    const val TOOLKIT_PREFERENCES = 37
    const val REMOVE_IGNORE = 38
    const val AP_COORD_T = 39
    const val INTERFACE_ON_PLAYER = 40
    const val RANK_FRIEND_CHAT = 41
    const val ITEM_ON_OBJECT = 42
    const val PLAYER_OPTION_9 = 43
    const val CLAN_SETTINGS_UPDATE = 44
    const val FLOOR_ITEM_OPTION_1 = 45
    const val PLAYER_OPTION_4 = 46
    const val OBJECT_OPTION_6 = 47
    const val CUTSCENE_ACTION = 48
    const val PLAYER_OPTION_7 = 49
    const val PLAYER_OPTION_5 = 50
    const val ADD_FRIEND = 51
    const val INTERFACE_OPTION_4 = 52
    const val PLAYER_OPTION_2 = 53
    const val DIALOGUE_CONTINUE = 54
    const val FLOOR_ITEM_OPTION_2 = 55
    const val SCREEN_CLOSE = 56
    const val ONLINE_STATUS = 57
    const val UNKNOWN = 58
    const val STRING_ENTRY = 59
    const val CLAN_CHAT_KICK = 60
    const val INTERFACE_OPTION_1 = 61
    const val PLAYER_OPTION_8 = 62
    const val OTHER_TELEPORT = 63
    const val INTERFACE_OPTION_2 = 64
    const val INTERFACE_ON_NPC = 65
    const val NPC_OPTION_2 = 66
    const val NPC_OPTION_4 = 67
    const val KEY_TYPED = 68
    const val OBJECT_OPTION_5 = 69
    const val CONSOLE_COMMAND = 70
    const val REGION_LOADING = 71
    const val PRIVATE_MESSAGE = 72
    const val ITEM_ON_ITEM = 73
    const val CLAN_FORUM_THREAD = 74
    const val IN_OUT_SCREEN = 75
    const val OBJECT_OPTION_3 = 76
    const val PLAYER_OPTION_3 = 77
    const val REFLECTION_RESPONSE = 78
    const val QUICK_PRIVATE_MESSAGE = 79
    const val REPORT_ABUSE = 80
    const val INTERFACE_OPTION_5 = 81
    const val PLAYER_OPTION_6 = 82
    const val MINI_MAP_WALK = 83
    const val CLICK = 84
    const val PING_LATENCY = 85
    const val FLOOR_ITEM_OPTION_4 = 86
    const val SCREEN_CHANGE = 87
    const val HYPERLINK_TEXT = 88
    const val WORLD_MAP_CLICK = 89
    const val SCRIPT_4701 = 90
    const val INTERFACE_OPTION_6 = 91
    const val NPC_OPTION_6 = 92
    const val TOGGLE_FOCUS = 93

    /* Encode */

    const val PLAYER_OPTION = 1
    const val INTERFACE_SPRITE = 2
    const val LOGIN_DETAILS = 2
    const val LOBBY_DETAILS = 2
    const val INTERFACE_COMPONENT_SETTINGS = 3
    const val INTERFACE_REFRESH = 4
    const val INTERFACE_OPEN = 5
    const val NPC_UPDATING = 6
    const val INTERFACE_SCROLL_VERTICAL = 8
    const val INTERFACE_ITEM = 9
    const val OBJECT_PRE_FETCH = 11
    const val FRIENDS_CHAT_UPDATE = 12
    const val RUN_ENERGY = 13
    const val CLIENT_VARBIT = 14
    const val FLOOR_ITEM_REMOVE = 16
    const val INTERFACE_PLAYER_BODY = 18
    const val FRIENDS_QUICK_CHAT_MESSAGE = 20
    const val CHUNK_CLEAR = 26
    const val INTERFACE_ANIMATION = 23
    const val FRIEND_LIST_APPEND = 24
    const val CLIENT_PING = 25
    const val OBJECT_ADD = 28
    const val TILE_TEXT = 32
    const val INTERFACE_TEXT = 33
    const val INTERFACE_ITEMS = 37
    const val CLIENT_VARP_LARGE = 39
    const val FRIENDS_CHAT_MESSAGE = 40
    const val GRAPHIC_AREA = 41
    const val PRIVATE_QUICK_CHAT_FROM = 42
    const val REGION = 43
    const val INTERFACE_CUSTOM_HEAD = 44
    const val OBJECT_REMOVE = 45
    const val UPDATE_CHUNK = 46
    const val OBJECT_ANIMATION_SPECIFIC = 47
    const val FLOOR_ITEM_ADD = 48
    const val SCRIPT = 50
    const val LOGOUT = 51
    const val CLIENT_VARC_STR = 54
    const val IGNORE_LIST = 57
    const val INTERFACE_MODEL = 58
    const val LOGOUT_LOBBY = 59
    const val FLOOR_ITEM_REVEAL = 60
    const val PROJECTILE_ADD = 62
    const val MINI_SOUND = 65
    const val INTERFACE_WINDOW = 67
    const val PLAYER_UPDATING = 69
    const val INTERFACE_CLOSE = 73
    const val PRIVATE_CHAT_TO = 77
    const val INTERFACE_ITEMS_UPDATE = 80
    const val FLOOR_ITEM_UPDATE = 83
    const val CLIENT_VARBIT_LARGE = 84
    const val FRIEND_LIST = 85
    const val WORLD_LIST = 88
    const val PROJECTILE_DISPLACE = 90
    const val PUBLIC_CHAT = 91
    const val SKILL_LEVEL = 93
    const val OBJECT_ANIMATION = 96
    const val PRIVATE_QUICK_CHAT_TO = 97
    const val INTERFACE_NPC_HEAD = 98
    const val OBJECT_CUSTOMISE = 99
    const val CLIENT_VARP = 101
    const val CHAT = 102
    const val INTERFACE_COMPONENT_POSITION = 104
    const val IGNORE_LIST_UPDATE = 105
    const val INTERFACE_PLAYER_OTHER_BODY = 109
    const val CLIENT_VARC = 111
    const val CLIENT_VARC_LARGE = 112
    const val INTERFACE_PLAYER_HEAD = 114
    const val INTERFACE_COMPONENT_VISIBILITY = 117
    const val SOUND_AREA = 119
    const val PRIVATE_CHAT_FROM = 120
    const val INTERFACE_COMPONENT_ORIENTATION = 122
    const val DYNAMIC_REGION = 128
    const val PRIVATE_STATUS = 134
    const val FRIEND_LIST_DISCONNECT = 135

    /* Handshake */
    const val LOGIN_HANDSHAKE = 14
    const val GAME_LOGIN = 16
    const val LOBBY_LOGIN = 19
}