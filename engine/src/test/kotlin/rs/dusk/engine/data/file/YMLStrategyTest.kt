package rs.dusk.engine.data.file

import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.io.file.FileIO
import rs.dusk.engine.io.player.strategy.YMLStrategy

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 04, 2020
 */
@ExtendWith(MockKExtension::class)
internal class YMLStrategyTest {

    /*
        inline functions can't be mocked so we can only test negative here.
     */
    @Test
    fun load() {
        // Given
        val loader = mockk<FileIO>()
        mockkStatic(FileIO::class)
        val storage = YMLStrategy("test")
        // When
        val result = storage.load("name")
        // Then
        assertNull(result)
    }

    @Test
    fun save() {
        // Given
        val loader = mockk<FileIO>(relaxed = true)
        val storage = YMLStrategy("test")
        val player = mockk<Player>(relaxed = true)
        // When
        storage.save("name", player)
        // Then
        verify { loader.save("test\\name.yml", player) }
    }
}