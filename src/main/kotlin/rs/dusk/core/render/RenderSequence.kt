package rs.dusk.core.render

import org.koin.dsl.module
import rs.dusk.game.entity.character.CharacterList
import rs.dusk.game.entity.character.player.Player
import rs.dusk.game.world.World
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit.MILLISECONDS

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 18, 2020
 */
class RenderSequence {
	
	fun render() {
		val players = World.gamePlayers
		
		run(players)
		finish(players)
	}
	
	/**
	 * Runs the updating part of the sequence
	 */
	private fun run(players : CharacterList<Player>) {
		val latch = CountDownLatch(players.size)
		for (player in players) {
			player.session?.send(player.rendering.createPacketAndProcess())
		}
		try {
			latch.await(600, MILLISECONDS)
		} catch (e : InterruptedException) {
			e.printStackTrace()
		}
	}
	
	/**
	 * Finishes the update sequence
	 */
	private fun finish(players : CharacterList<Player>) {
		for (player in players) {
			// reset masks
			// flush everything out
		}
	}
	
}

val renderSequenceModule = module {
	single(createdAtStart = true) { RenderSequence() }
}