package rs.dusk.engine.entity.character.combat

import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import org.junit.Test
import rs.dusk.engine.entity.character.combat.CombatHandler.Companion.NO_TARGET
import rs.dusk.engine.entity.character.combat.strategy.MeleeStrategy
import rs.dusk.engine.entity.character.npc.NPC
import rs.dusk.engine.entity.character.player.Player

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/26/20 9:03 PM
 **/
internal class CombatHandlerTest {



    companion object {
        lateinit var player: Player
        lateinit var npc: NPC
        lateinit var playerHandler: CombatHandler
        lateinit var npcHandler: CombatHandler
        lateinit var melee: CombatStrategy
        @BeforeClass
        @JvmStatic
        fun setup() {
            player = mockk(relaxed = true)
            npc = mockk(relaxed = true)
            playerHandler = CombatHandler()
            npcHandler = CombatHandler()
            melee = MeleeStrategy()
        }
    }

    @Test
    fun `combat event test`() {

        playerHandler.bind(npc)

        assert(playerHandler.focusedTarget.value.character === npc) { "Failed to bind to target" }

        runBlocking {
            playerHandler attack npc with melee
        }

        playerHandler.unbind()

        assert(playerHandler.focusedTarget.value === NO_TARGET) { "Failed to Unbind target." }

    }

}
