package rs.dusk.game.model.player

import org.junit.jupiter.api.Test
import rs.dusk.engine.entity.character.player.Player

/**
 * @author Tyluur <itstyluur@gmail.com>
 *
 * @since December 04, 2020
 */
internal class PlayerIOTest {
	
	lateinit var playerIO : PlayerIO
	
	@Test
	fun `save_player_file_using_yaml`() {
		val player = Player(login = "player")
		val out = playerIO.save(player)
		
		println("out=$out")
	}
}