package rs.dusk.game.entity.character.player.render

import com.github.michaelbull.logging.InlineLogger
import rs.dusk.core.network.packet.PacketType
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.game.entity.character.player.Player
import rs.dusk.game.world.World

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 18, 2020
 */
class PlayerRendering(val player : Player) {
	
	val localPlayers : Array<Player?> = arrayOfNulls(2048)
	
	val localPlayersIndexes : IntArray = IntArray(2048)
	
	var localPlayersIndexesCount = 0
	
	val outPlayersIndexes : IntArray = IntArray(2048)
	
	var outPlayersIndexesCount = 0
	
	val regionHashes : IntArray = IntArray(2048)
	
	val cachedAppearencesHashes : Array<ByteArray?> = arrayOfNulls(2048)
	
	var totalRenderDataSentLength = 0
	
	fun init(stream : PacketWriter) {
		stream.startBitAccess()
		stream.writeBits(30, player.tile.get30BitsLocationHash())
		localPlayers[player.index] = player
		localPlayersIndexes[localPlayersIndexesCount++] = player.index
		for (playerIndex in 1..2047) {
			if (playerIndex == player.index) {
				continue
			}
			val player = World.findPlayerByIndex(playerIndex)
			stream.writeBits(18, player?.tile?.get18BitsLocationHash()?.also({ regionHashes[playerIndex] = it }) ?: 0)
			outPlayersIndexes[outPlayersIndexesCount++] = playerIndex
		}
		stream.finishBitAccess()
	}
	
	
	fun createPacketAndProcess() : PacketWriter {
		val stream = PacketWriter()
		stream.writeOpcode(69, PacketType.SHORT)
		val updateBlockData = PacketWriter()
		processLocalPlayers(stream, updateBlockData)
		processOutsidePlayers(stream, updateBlockData)
		stream.writeBytes(updateBlockData.buffer)
		totalRenderDataSentLength = 0
		localPlayersIndexesCount = 0
		outPlayersIndexesCount = 0
		for (playerIndex in 1..2047) {
			val player = localPlayers[playerIndex]
			if (player == null) {
				outPlayersIndexes[outPlayersIndexesCount++] = playerIndex
			} else {
				localPlayersIndexes[localPlayersIndexesCount++] = playerIndex
			}
		}
		return stream
	}
	
	private fun processOutsidePlayers(stream : PacketWriter, updateBlockData : PacketWriter) {
		stream.startBitAccess()
		var skip = 0
		for (i in 0 until outPlayersIndexesCount) {
			val playerIndex = outPlayersIndexes[i]
			if (skip > 0) {
				skip--
				continue
			}
			val p = World.findPlayerByIndex(playerIndex)
			/*if (needsAdd(p)) {
				stream.writeBits(1, 1)
				stream.writeBits(2, 0) // request add
				val hash : Int = p.tile.get18BitsLocationHash()
				if (hash == regionHashes[playerIndex]) {
					stream.writeBits(1, 0)
				} else {
					stream.writeBits(1, 1)
					updateRegionHash(stream, regionHashes[playerIndex], hash)
					regionHashes[playerIndex] = hash
				}
				stream.writeBits(6, p.getXInRegion())
				stream.writeBits(6, p.getYInRegion())
				val needAppearenceUpdate : Boolean = needAppearenceUpdate(p.index, p.getAppearance().getMd5Hash())
				appendUpdateBlock(p, updateBlockData, needAppearenceUpdate, true)
				stream.writeBits(1, 1)
				localPlayers[p.index] = p
			} else {
				val hash = if (p == null) regionHashes[playerIndex] else p.tile.get18BitsLocationHash()
				if (p != null && hash != regionHashes[playerIndex]) {
					stream.writeBits(1, 1)
					updateRegionHash(stream, regionHashes[playerIndex], hash)
					regionHashes[playerIndex] = hash
				} else {
					stream.writeBits(1, 0) // no update needed
					for (i2 in i + 1 until outPlayersIndexesCount) {
						val p2Index = outPlayersIndexes[i2]
						val p2 = World.findPlayerByIndex(p2Index)
						if (needsAdd(p2) || p2 != null && p2.tile.get18BitsLocationHash() !== regionHashes.get(p2Index)) {
							break
						}
						skip++
					}
					skipPlayers(stream, skip)
				}
			}*/
		}
		stream.finishBitAccess()
	}
	
	private fun processLocalPlayers(stream : PacketWriter, updateBlockData : PacketWriter) {
		stream.startBitAccess()
		var skip = 0
		for (i in 0 until localPlayersIndexesCount) {
			val playerIndex = localPlayersIndexes[i]
			if (skip > 0) {
				skip--
				continue
			}
			val o = localPlayers[playerIndex]!!
			if (needsRemove(o)) {
				/*stream.writeBits(1, 1) // needs update
				stream.writeBits(1, 0) // no masks update needeed
				stream.writeBits(2, 0) // request remove
				regionHashes[playerIndex] =
					if (o.getLastWorldTile() == null) o.get18BitsLocationHash() else o.getLastWorldTile()
						.get18BitsLocationHash()
				val hash : Int = o.get18BitsLocationHash()
				if (hash == regionHashes[playerIndex]) {
					stream.writeBits(1, 0)
				} else {
					stream.writeBits(1, 1)
					updateRegionHash(stream, regionHashes[playerIndex], hash)
					regionHashes[playerIndex] = hash
				}
				localPlayers[playerIndex] = null*/
			} else {
				/*val needAppearanceUpdate : Boolean = needAppearenceUpdate(o.index, o.getAppearance().getMd5Hash())
				val needUpdate = o.needMasksUpdate() || needAppearanceUpdate
				if (needUpdate) {
					appendUpdateBlock(o, updateBlockData, needAppearanceUpdate, false)
				}
				if (o.hasTeleported()) {
					stream.writeBits(1, 1) // needs update
					stream.writeBits(1, if (needUpdate) 1 else 0)
					stream.writeBits(2, 3)
					var xOffset : Int = o.getX() - o.getLastWorldTile().getX()
					var yOffset : Int = o.getY() - o.getLastWorldTile().getY()
					val planeOffset : Int = o.getPlane() - o.getLastWorldTile().getPlane()
					// 14 for safe (?)
					if (Math.abs(
							o.getX() - o.getLastWorldTile().getX()
						) <= 14 && Math.abs(o.getY() - o.getLastWorldTile().getY()) <= 14
					) {
						stream.writeBits(1, 0)
						if (xOffset < 0) {
							xOffset += 32
						}
						if (yOffset < 0) {
							yOffset += 32
						}
						stream.writeBits(12, yOffset + (xOffset shl 5) + (planeOffset shl 10))
					} else {
						stream.writeBits(1, 1)
						stream.writeBits(
							30,
							(yOffset and 0x3fff) + (xOffset and 0x3fff shl 14) + (planeOffset and 0x3 shl 28)
						)
					}
				} else if (o.getNextWalkDirection() !== -1) {
					var dx : Int = Misc.DIRECTION_DELTA_X.get(o.getNextWalkDirection())
					var dy : Int = Misc.DIRECTION_DELTA_Y.get(o.getNextWalkDirection())
					var running : Boolean
					var opcode : Int
					if (o.getNextRunDirection() !== -1) {
						dx += Misc.DIRECTION_DELTA_X.get(o.getNextRunDirection())
						dy += Misc.DIRECTION_DELTA_Y.get(o.getNextRunDirection())
						opcode = Misc.getPlayerRunningDirection(dx, dy)
						if (opcode == -1) {
							running = false
							opcode = Misc.getPlayerWalkingDirection(dx, dy)
						} else {
							running = true
						}
					} else {
						running = false
						opcode = Misc.getPlayerWalkingDirection(dx, dy)
					}
					stream.writeBits(1, 1)
					if (dx == 0 && dy == 0) {
						stream.writeBits(1, 1) // quick fix
						stream.writeBits(2, 0)
						// hasn't been sent yet
						if (!needUpdate) {
							appendUpdateBlock(o, updateBlockData, needAppearanceUpdate, false)
						}
					} else {
						stream.writeBits(1, if (needUpdate) 1 else 0)
						stream.writeBits(2, if (running) 2 else 1)
						stream.writeBits(if (running) 4 else 3, opcode)
					}
				} else if (needUpdate) {
					stream.writeBits(1, 1) // needs update
					stream.writeBits(1, 1)
					stream.writeBits(2, 0)
				} else { // skip
					stream.writeBits(1, 0) // no update needed
					for (i2 in i + 1 until localPlayersIndexesCount) {
						val p2Index = localPlayersIndexes[i2]
						val p2 = localPlayers[p2Index]!!
						if (needsRemove(p2)/* || p2.hasTeleported() || p2.getNextWalkDirection() !== -1 || p2.needMasksUpdate() || needAppearenceUpdate(
								p2.index,
								p2.getAppearance().getMd5Hash()
							)*/
						) {
							break
						}
						skip++
					}
					skipPlayers(stream, skip)
				}*/
			}
		}
		stream.finishBitAccess()
	}
	
	private fun skipPlayers(stream : PacketWriter, amount : Int) {
		stream.writeBits(2, if (amount == 0) 0 else if (amount > 255) 3 else if (amount > 31) 2 else 1)
		if (amount > 0) {
			stream.writeBits(if (amount > 255) 11 else if (amount > 31) 8 else 5, amount)
		}
	}
	
	private fun needsAdd(p : Player?) : Boolean {
		return p != null && !p.removed && withinDistance(p)
	}
	
	private fun needsRemove(p : Player) : Boolean {
		return p.removed || !withinDistance(p)
	}
	
	fun withinDistance(other : Player) : Boolean {
		if (other.tile.plane !== player.tile.plane) {
			return false
		}
		return Math.abs(other.tile.x - player.tile.x) <= 14 && Math.abs(other.tile.y - player.tile.y) <= 14
	}
	
	companion object {
		
		private val logger = InlineLogger()
		
	}
	
}