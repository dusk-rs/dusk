package rs.dusk.engine.client.ui

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import rs.dusk.engine.client.ui.Interfaces.Companion.ROOT_ID
import rs.dusk.engine.client.ui.Interfaces.Companion.ROOT_INDEX
import rs.dusk.engine.client.ui.detail.InterfaceData
import rs.dusk.engine.client.ui.detail.InterfaceDetail
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.entity.character.player.PlayerGameFrame
import rs.dusk.engine.entity.character.player.setDisplayMode

internal class PlayerGameFrameTest : InterfaceTest() {

    private lateinit var player: Player
    lateinit var detail1: InterfaceDetail
    lateinit var detail2: InterfaceDetail

    @BeforeEach
    override fun setup() {
        super.setup()
        player = mockk(relaxed = true)
        every { player.gameFrame } returns gameframe
        every { player.interfaces } returns manager
        detail1 = InterfaceDetail(id = 123, data = InterfaceData(resizableParent = ROOT_ID, resizableIndex = ROOT_INDEX))
        detail2 = InterfaceDetail(id = 124, data = InterfaceData(fixedParent = ROOT_ID, fixedIndex = ROOT_INDEX))
        interfaces["toplevel_full"] = detail1
        interfaces["toplevel"] = detail2
        names[123] = "toplevel_full"
        names[124] = "toplevel"
    }

    @Test
    fun `Don't set top level size if full not open`() {
        val result = player.setDisplayMode(PlayerGameFrame.FIXED_SCREEN)
        assertFalse(result)
    }

    @Test
    fun `Don't set full if top level not open`() {
        val result = player.setDisplayMode(PlayerGameFrame.RESIZABLE_SCREEN)
        assertFalse(result)
    }

    @Test
    fun `Size set top level if full open`() {
        gameframe.resizable = true
        manager.open("toplevel_full")
        val result = player.setDisplayMode(PlayerGameFrame.FIXED_SCREEN)
        assertTrue(result)
        assertEquals(false, gameframe.resizable)
    }

    @Test
    fun `Size set full if top level open`() {
        manager.open("toplevel")
        val result = player.setDisplayMode(PlayerGameFrame.RESIZABLE_SCREEN)
        assertTrue(result)
        assertEquals(true, gameframe.resizable)
    }

    @Test
    fun `Fixed screen`() {
        assertFalse(gameframe.resizable)
    }
}
