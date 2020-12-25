package rs.dusk.game.container.player

import inject
import rs.dusk.cache.definition.entity.ItemDefinitionReader
import rs.dusk.game.entity.character.player.Player

/**
 * @author Tyluur <itstyluur></itstyluur>@gmail.com>
 * @since 8/31/2017
 */
interface EquipmentConstants {
	
	companion object {
		private val itemDefinitionInstance : ItemDefinitionReader by inject()
		
		fun isFullBody(item : Item) : Boolean {
			var itemName : String = item.definitions.name
			if (itemName == null) {
				return false
			}
			itemName = itemName.toLowerCase()
			for (i in NOT_FULL_BODY.indices) {
				if (itemName.contains(NOT_FULL_BODY[i].toLowerCase())) {
					return false
				}
			}
			for (i in FULL_BODY.indices) {
				if (itemName.contains(FULL_BODY[i].toLowerCase())) {
					return true
				}
			}
			return false
		}
		
		fun isFullHat(item : Item) : Boolean {
			var itemName : String = item.definitions.name
			if (itemName == null) {
				return false
			}
			itemName = itemName.toLowerCase()
			for (i in FULL_HAT.indices) {
				if (itemName.contains(FULL_HAT[i].toLowerCase())) {
					return true
				}
			}
			return false
		}
		
		fun isFullMask(item : Item) : Boolean {
			var itemName : String = item.definitions.name
			if (itemName == null) {
				return false
			}
			itemName = itemName.toLowerCase()
			for (i in FULL_MASK.indices) {
				if (itemName.contains(FULL_MASK[i].toLowerCase())) {
					return true
				}
			}
			return false
		}
		
		fun sendRemove(player : Player, slotId : Int) {
			println("player = [${player}], slotId = [${slotId}]")
			/*if (player.getLocks().isEquipmentLocked() || slotId >= 15) {
				return
			}
			val item : Item = player.getEquipment().getItem(slotId)
			if (item == null || !player.getInventory().addItem(item.id, item.amount)) {
				return
			}
			player.getEquipment().getItems().set(slotId, null)
			player.getEquipment().refresh(slotId)
			player.appearance.generateAppearanceData()
			if (Runecrafting.isTiara(item.id)) {
				player.getPackets().sendConfig(491, 0)
			}
			if (slotId == 3) {
				player.getCombatDefinitions().decreaseSpecialEnergy(0)
			}*/
		}
		
		fun sendWear(player : Player, slotId : Int, itemId : Int) : Boolean {
			/*if (player.isFinished() || player.isDead()) {
				return false
			}
			val item : Item = player.getInventory().getItem(slotId)
			if (item == null || item.id != itemId) {
				return false
			}
			if (item.getDefinitions().isNoted() || !item.getDefinitions()
					.isWearItem(player.appearance.isMale()) && item.getDefinitions().getId() !== 4084
			) {
				player.getPackets().sendMessage("You can't wear that.")
				return true
			}
			val targetSlot = getItemSlot(itemId)
			if (targetSlot == -1) {
				player.getPackets().sendMessage("You can't wear that.")
				return true
			}
			val isTwoHandedWeapon = targetSlot == 3 && isTwoHandedWeapon(item)
			if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots() && player.getEquipment().hasShield()) {
				player.getPackets().sendMessage("Not enough free space in your inventory.")
				return true
			}
			val requiriments : HashMap<Int, Int> = item.getDefinitions().getWearingSkillRequirements()
			var hasRequiriments = true
			if (requiriments != null) {
				for (skillId in requiriments.keys) {
					if (skillId > 24 || skillId < 0) {
						continue
					}
					val level = requiriments[skillId]!!
					if (level < 0 || level > 120) {
						continue
					}
					if (player.getSkills().getLevelForXp(skillId) < level) {
						if (hasRequiriments) {
							player.getPackets().sendMessage("You are not high enough level to use this item.")
						}
						hasRequiriments = false
						val name : String = SkillConstants.SKILL_NAME.get(skillId).toLowerCase()
						player.getPackets()
							.sendMessage("You need to have a" + (if (name.startsWith("a")) "n" else "") + " " + name + " level of " + level + ".")
					}
				}
			}
			if (!hasRequiriments) {
				return true
			}
			if (!player.getControllerManager().canEquip(targetSlot, itemId)) {
				return false
			}
			player.getInventory().deleteItem(slotId, item)
			if (targetSlot == 3) {
				if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
					if (!player.getInventory().addItem(
							player.getEquipment().getItem(5).getId(),
							player.getEquipment().getItem(5).getAmount()
						)
					) {
						player.getInventory().getItems().set(slotId, item)
						player.getInventory().refresh(slotId)
						return true
					}
					player.getEquipment().getItems().set(5, null)
				}
			} else if (targetSlot == 5) {
				if (player.getEquipment().getItem(3) != null && isTwoHandedWeapon(player.getEquipment().getItem(3))) {
					if (!player.getInventory().addItem(
							player.getEquipment().getItem(3).getId(),
							player.getEquipment().getItem(3).getAmount()
						)
					) {
						player.getInventory().getItems().set(slotId, item)
						player.getInventory().refresh(slotId)
						return true
					}
					player.getEquipment().getItems().set(3, null)
				}
			}
			if (player.getEquipment().getItem(targetSlot) != null && (itemId != player.getEquipment()
					.getItem(targetSlot).getId() || !item.getDefinitions().isStackable())
			) {
				if (player.getInventory().getItems().get(slotId) == null) {
					player.getInventory().getItems().set(
						slotId,
						Item(
							player.getEquipment().getItem(targetSlot).getId(),
							player.getEquipment().getItem(targetSlot).getAmount()
						)
					)
					player.getInventory().refresh(slotId)
				} else {
					player.getInventory().addItem(
						Item(
							player.getEquipment().getItem(targetSlot).getId(),
							player.getEquipment().getItem(targetSlot).getAmount()
						)
					)
				}
				player.getEquipment().getItems().set(targetSlot, null)
			}
			var oldAmt = 0
			if (player.getEquipment().getItem(targetSlot) != null) {
				oldAmt = player.getEquipment().getItem(targetSlot).getAmount()
			}
			val item2 = Item(itemId, oldAmt + item.amount)
			player.getEquipment().getItems().set(targetSlot, item2)
			player.getEquipment().refresh(targetSlot, if (targetSlot == 3) 5 else if (targetSlot == 3) 0 else 3)
			player.appearance.generateAppearanceData()
			player.getPackets().sendSound(2240, 0, 1)
			if (targetSlot == 3) {
				player.getCombatDefinitions().decreaseSpecialEnergy(0)
			}
			player.getCharges().wear(targetSlot)*/
			println("player = [${player}], slotId = [${slotId}], itemId = [${itemId}]")
			return true
		}
		
		fun getItemSlot(itemId : Int) : Int {
			for (i in BODY_LIST.indices) {
				if (itemId == BODY_LIST[i]) {
					return 4
				}
			}
			for (i in LEGS_LIST.indices) {
				if (itemId == LEGS_LIST[i]) {
					return 7
				}
			}
			val item : String = itemDefinitionInstance.get(itemId).name.toLowerCase() ?: return -1
			for (i in CAPES.indices) {
				if (item.contains(CAPES[i].toLowerCase())) {
					return 1
				}
			}
			for (i in BOOTS.indices) {
				if (item.contains(BOOTS[i].toLowerCase())) {
					return 10
				}
			}
			for (i in GLOVES.indices) {
				if (item.contains(GLOVES[i].toLowerCase())) {
					return 9
				}
			}
			for (i in SHIELDS.indices) {
				if (item.contains(SHIELDS[i].toLowerCase())) {
					return 5
				}
			}
			for (i in AMULETS.indices) {
				if (item.contains(AMULETS[i].toLowerCase())) {
					return 2
				}
			}
			for (i in ARROWS.indices) {
				if (item.contains(ARROWS[i].toLowerCase())) {
					return 13
				}
			}
			for (i in RINGS.indices) {
				if (item.contains(RINGS[i].toLowerCase())) {
					return 12
				}
			}
			for (i in WEAPONS.indices) {
				if (item.contains(WEAPONS[i].toLowerCase())) {
					return 3
				}
			}
			if (itemId == 4084) {
				return 3
			}
			for (i in HATS.indices) {
				if (item.contains(HATS[i].toLowerCase())) {
					return 0
				}
			}
			for (i in BODY.indices) {
				if (item.contains(BODY[i].toLowerCase())) {
					return 4
				}
			}
			for (i in LEGS.indices) {
				if (item.contains(LEGS[i].toLowerCase())) {
					return 7
				}
			}
			for (i in AURAS.indices) {
				if (item.contains(AURAS[i].toLowerCase())) {
					return SLOT_AURA.toInt()
				}
			}
			return -1
		}
		
		fun isTwoHandedWeapon(item : Item) : Boolean {
			val itemId = item.id
			if (itemId == 4212) {
				return true
			}
			if (itemId == 4084) {
				return true
			} else if (itemId == 4214) {
				return true
			} else if (itemId == 20281) {
				return true
			}
			val wepEquiped : String = item.definitions.name.toLowerCase()
			if (wepEquiped == null) {
				return false
			} else if (wepEquiped == "stone of power") {
				return true
			} else if (wepEquiped == "dominion sword") {
				return true
			} else if (wepEquiped.endsWith("claws")) {
				return true
			} else if (wepEquiped.endsWith("anchor")) {
				return true
			} else if (wepEquiped.contains("2h sword")) {
				return true
			} else if (wepEquiped.contains("katana")) {
				return true
			} else if (wepEquiped == "seercull") {
				return true
			} else if (wepEquiped.contains("shortbow")) {
				return true
			} else if (wepEquiped.contains("longbow")) {
				return true
			} else if (wepEquiped.contains("shortbow")) {
				return true
			} else if (wepEquiped.contains("bow full")) {
				return true
			} else if (wepEquiped == "zaryte bow") {
				return true
			} else if (wepEquiped == "dark bow") {
				return true
			} else if (wepEquiped.endsWith("halberd")) {
				return true
			} else if (wepEquiped.contains("maul")) {
				return true
			} else if (wepEquiped == "karil's crossbow") {
				return true
			} else if (wepEquiped == "torag's hammers") {
				return true
			} else if (wepEquiped == "verac's flail") {
				return true
			} else if (wepEquiped.contains("greataxe")) {
				return true
			} else if (wepEquiped.contains("spear")) {
				return true
			} else if (wepEquiped == "tzhaar-ket-om") {
				return true
			} else if (wepEquiped.contains("godsword")) {
				return true
			} else if (wepEquiped == "saradomin sword") {
				return true
			} else if (wepEquiped == "hand cannon") {
				return true
			}
			return false
		}
		
		const val SLOT_HAT : Byte = 0
		const val SLOT_CAPE : Byte = 1
		const val SLOT_AMULET : Byte = 2
		const val SLOT_WEAPON : Byte = 3
		const val SLOT_CHEST : Byte = 4
		const val SLOT_SHIELD : Byte = 5
		const val SLOT_LEGS : Byte = 7
		const val SLOT_HANDS : Byte = 9
		const val SLOT_FEET : Byte = 10
		const val SLOT_RING : Byte = 12
		const val SLOT_ARROWS : Byte = 13
		const val SLOT_AURA : Byte = 14
		val DISABLED_SLOTS = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0)
		val CAPES = arrayOf("cloak", "cape", "ava's", "tokhaar")
		val HATS = arrayOf(
			"visor",
			"ears",
			"goggles",
			"bearhead",
			"tiara",
			"cowl",
			"druidic wreath",
			"halo",
			"crown",
			"sallet",
			"helm",
			"hood",
			"coif",
			"flaming skull",
			"Coif",
			"partyhat",
			"hat",
			"cap",
			" bandana",
			"full helm (t)",
			"full helm (g)",
			"full helm (or)",
			"cav",
			"boater",
			"helmet",
			"afro",
			"beard",
			"gnome goggles",
			"mask",
			"Helm of neitiznot",
			"mitre",
			"nemes",
			"wig",
			"headdress",
			"double eyepatches"
		)
		val BOOTS = arrayOf("boots", "Boots", "shoes", "Shoes", "flippers")
		val GLOVES = arrayOf("gloves", "gauntlets", "Gloves", "vambraces", "vamb", "bracers", "brace")
		val AMULETS = arrayOf("stole", "amulet", "necklace", "Amulet of", "scarf", "Super dominion medallion")
		val SHIELDS = arrayOf(
			"tome of frost",
			"kiteshield",
			"sq shield",
			"Toktz-ket",
			"books",
			"book",
			"kiteshield (t)",
			"kiteshield (g)",
			"kiteshield(h)",
			"defender",
			"shield",
			"deflector"
		)
		val ARROWS = arrayOf(
			"arrow",
			"arrows",
			"arrow(p)",
			"arrow(+)",
			"arrow(s)",
			"bolt",
			"Bolt rack",
			"Opal bolts",
			"Dragon bolts",
			"bolts (e)",
			"bolts",
			"Hand cannon shot"
		)
		val RINGS = arrayOf("ring")
		val BODY = arrayOf(
			"poncho",
			"apron",
			"robe top",
			"armour",
			"hauberk",
			"platebody",
			"chainbody",
			"breastplate",
			"blouse",
			"robetop",
			"leathertop",
			"platemail",
			"top",
			"brassard",
			"body",
			"platebody (t)",
			"platebody (g)",
			"body(g)",
			"body_(g)",
			"chestplate",
			"torso",
			"shirt",
			"Rock-shell plate",
			"coat",
			"jacket"
		)
		val AURAS = arrayOf(
			"poison purge",
			"Salvation",
			"Corruption",
			"salvation",
			"corruption",
			"runic accuracy",
			"sharpshooter",
			"lumberjack",
			"quarrymaster",
			"call of the sea",
			"reverence",
			"five finger discount",
			"resourceful",
			"equilibrium",
			"inspiration",
			"vampyrism",
			"penance",
			"wisdom",
			"jack of trades",
			"gaze"
		)
		val BODY_LIST = intArrayOf(21463, 21549, 544, 6107)
		val LEGS_LIST = intArrayOf(542, 6108, 10340, 7398)
		val LEGS = arrayOf(
			"leggings",
			"void knight robe",
			"druidic robe",
			"cuisse",
			"pants",
			"platelegs",
			"plateskirt",
			"skirt",
			"bottoms",
			"chaps",
			"platelegs (t)",
			"platelegs (or)",
			"platelegs (g)",
			"bottom",
			"skirt",
			"skirt (g)",
			"skirt (t)",
			"chaps (g)",
			"chaps (t)",
			"tassets",
			"legs",
			"trousers",
			"robe bottom",
			"shorts",
			"black navy slacks"
		)
		val WEAPONS = arrayOf(
			"bolas",
			"stick",
			"blade",
			"Butterfly net",
			"scythe",
			"rapier",
			"hatchet",
			"bow",
			"Hand cannon",
			"Inferno adze",
			"Silverlight",
			"Darklight",
			"wand",
			"Statius's warhammer",
			"anchor",
			"spear.",
			"Vesta's longsword.",
			"scimitar",
			"longsword",
			"sword",
			"longbow",
			"shortbow",
			"dagger",
			"mace",
			"halberd",
			"spear",
			"Abyssal whip",
			"Abyssal vine whip",
			"Ornate katana",
			"axe",
			"flail",
			"crossbow",
			"Torags hammers",
			"dagger(p)",
			"dagger (p++)",
			"dagger(+)",
			"dagger(s)",
			"spear(p)",
			"spear(+)",
			"spear(s)",
			"spear(kp)",
			"maul",
			"dart",
			"dart(p)",
			"javelin",
			"javelin(p)",
			"knife",
			"knife(p)",
			"Longbow",
			"Shortbow",
			"Crossbow",
			"Toktz-xil",
			"Shark fists",
			"Toktz-mej",
			"Tzhaar-ket",
			"staff",
			"Staff",
			"godsword",
			"c'bow",
			"Crystal bow",
			"Dark bow",
			"claws",
			"warhammer",
			"hammers",
			"adze",
			"hand",
			"Broomstick",
			"Flowers",
			"flowers",
			"trident",
			"excalibur",
			"cane",
			"sled",
			"Katana",
			"bag",
			"tenderiser",
			"eggsterminator",
			"Sled",
			"sceptre",
			"decimation",
			"obliteration",
			"annihilation",
			"queen maddie's toy"
		)
		val NOT_FULL_BODY = arrayOf("zombie shirt")
		val FULL_BODY = arrayOf(
			"robe",
			"breastplate",
			"blouse",
			"pernix body",
			"vesta's chainbody",
			"armour",
			"hauberk",
			"top",
			"shirt",
			"platebody",
			"Ahrims robetop",
			"Karils leathertop",
			"brassard",
			"chestplate",
			"torso",
			"Morrigan's",
			"Zuriel's",
			"changshan jacket"
		)
		val FULL_HAT = arrayOf(
			"helm",
			"cowl",
			"sallet",
			"med helm",
			"coif",
			"Dharoks helm",
			"Initiate helm",
			"Coif",
			"Helm of neitiznot"
		)
		val FULL_MASK = arrayOf(
			"sallet",
			"mask",
			"full helm",
			"mask",
			"Veracs helm",
			"Guthans helm",
			"Torags helm",
			"flaming skull",
			"Karils coif",
			"full helm (t)",
			"full helm (g)"
		)
	}
}