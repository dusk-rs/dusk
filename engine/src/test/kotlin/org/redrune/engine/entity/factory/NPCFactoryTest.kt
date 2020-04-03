package org.redrune.engine.entity.factory

import io.mockk.Runs
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.dsl.module
import org.koin.test.mock.declareMock
import org.redrune.engine.entity.event.Registered
import org.redrune.engine.event.EventBus
import org.redrune.engine.event.eventBusModule
import org.redrune.engine.model.Direction
import org.redrune.engine.model.Tile
import org.redrune.engine.script.KoinMock
import org.redrune.utility.get

/**
 * @author Greg Hibberd <greg></greg>@greghibberd.com>
 * @since March 30, 2020
 */
@ExtendWith(MockKExtension::class)
internal class NPCFactoryTest : KoinMock() {

    @BeforeEach
    fun setup() {
        loadModules(module { single { NPCFactory() } }, eventBusModule)
    }

    @Test
    fun `Spawn registers`() {
        // Given
        val factory: NPCFactory = get()
        val bus: EventBus = declareMock {
            every { emit(any<Registered>()) } just Runs
        }
        // When
        val npc = factory.spawn(1, 10, 20, 1, Direction.NONE)
        // Then
        assertEquals(1, npc.id)
        verify { bus.emit<Registered>(any()) }
        assertEquals(npc.tile, Tile(10, 20, 1))
    }

}