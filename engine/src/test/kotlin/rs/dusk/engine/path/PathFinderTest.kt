package rs.dusk.engine.path

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import rs.dusk.engine.model.entity.Entity
import rs.dusk.engine.model.entity.Size
import rs.dusk.engine.model.entity.index.Indexed
import rs.dusk.engine.model.entity.index.npc.NPC
import rs.dusk.engine.model.entity.index.player.Player
import rs.dusk.engine.model.entity.item.FloorItem
import rs.dusk.engine.model.entity.obj.Location
import rs.dusk.engine.model.world.Tile
import rs.dusk.engine.model.world.map.collision.Collisions
import rs.dusk.engine.path.find.BreadthFirstSearch
import rs.dusk.engine.path.obstruction.LargeObstruction
import rs.dusk.engine.path.obstruction.MediumObstruction
import rs.dusk.engine.path.obstruction.SmallObstruction
import rs.dusk.engine.path.target.*

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 21, 2020
 */
internal class PathFinderTest {
    lateinit var pf: PathFinder
    lateinit var collisions: Collisions
    lateinit var bfs: BreadthFirstSearch
    lateinit var small: SmallObstruction
    lateinit var medium: MediumObstruction
    lateinit var large: LargeObstruction

    @BeforeEach
    fun setup() {
        collisions = mockk(relaxed = true)
        bfs = mockk(relaxed = true)
        small = mockk(relaxed = true)
        medium = mockk(relaxed = true)
        large = mockk(relaxed = true)
        pf = spyk(PathFinder(collisions, bfs, small, medium, large))
    }

    @Test
    fun `Find tile`() {
        // Given
        val source: Indexed = mockk(relaxed = true)
        val target = Tile(1, 1)
        val obs: ObstructionStrategy = mockk(relaxed = true)
        every { pf.getObstructions(any()) } returns obs
        every { pf.getFinder(any()) } returns bfs
        // When
        pf.find(source, target)
        // Then
        verify { bfs.find(source.tile, source.size, source.movement, any<TileTargetStrategy>(), obs) }
    }

    @Test
    fun `Find entity`() {
        // Given
        val source: Indexed = mockk(relaxed = true)
        val target: Entity = mockk(relaxed = true)
        val obs: ObstructionStrategy = mockk(relaxed = true)
        val strategy: TargetStrategy = mockk(relaxed = true)
        every { pf.getObstructions(any()) } returns obs
        every { pf.getStrategy(any()) } returns strategy
        every { pf.getFinder(any()) } returns bfs
        // When
        pf.find(source, target)
        // Then
        verify { bfs.find(source.tile, source.size, source.movement, strategy, obs) }
    }

    @Test
    fun `Player finder`() {
        // Given
        val source: Player = mockk(relaxed = true)
        // When
        val finder = pf.getFinder(source)
        // Then
        assertEquals(bfs, finder)
    }

    @Test
    fun `NPC finder`() {
        // Given
        val source: NPC = mockk(relaxed = true)
        // When
        val finder = pf.getFinder(source)
        // Then
//        assertEquals(bfs, finder) TODO
    }

    @Test
    fun `Floor item strategy`() {
        // Given
        val target: FloorItem = mockk(relaxed = true)
        // When
        val strategy = pf.getStrategy(target)
        // Then
        assertEquals(PointTargetStrategy(target.tile, target.size), strategy)
    }

    @Test
    fun `Small obstruction`() {
        // Given
        val size = Size(1, 1)
        // When
        val obstruction = pf.getObstructions(size)
        // Then
        assertEquals(small, obstruction)
    }

    @Test
    fun `Medium obstruction`() {
        // Given
        val size = Size(2, 2)
        // When
        val obstruction = pf.getObstructions(size)
        // Then
        assertEquals(medium, obstruction)
    }

    @Test
    fun `Large obstruction`() {
        // Given
        val size = Size(1, 2)
        // When
        val obstruction = pf.getObstructions(size)
        // Then
        assertEquals(large, obstruction)
    }

    @Test
    fun `NPC strategy`() {
        // Given
        val target: NPC = mockk(relaxed = true)
        // When
        val strategy = pf.getStrategy(target)
        // Then
        assertEquals(RectangleTargetStrategy(collisions, target.tile, target.size), strategy)
    }

    @Test
    fun `Player strategy`() {
        // Given
        val target: Player = mockk(relaxed = true)
        // When
        val strategy = pf.getStrategy(target)
        // Then
        assertEquals(RectangleTargetStrategy(collisions, target.tile, target.size), strategy)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 9])
    fun `Wall location strategy`(type: Int) {
        // Given
        val target: Location = mockk(relaxed = true)
        every { target.type } returns type
        // When
        val strategy = pf.getStrategy(target)
        // Then
        assertEquals(WallTargetStrategy(collisions, target.tile, target.size, target.rotation, target.type), strategy)
    }

    @ParameterizedTest
    @ValueSource(ints = [3, 4, 5, 6, 7, 8])
    fun `Wall decoration location strategy`(type: Int) {
        // Given
        val target: Location = mockk(relaxed = true)
        every { target.type } returns type
        // When
        val strategy = pf.getStrategy(target)
        // Then
        assertEquals(
            DecorationTargetStrategy(collisions, target.tile, target.size, target.rotation, target.type),
            strategy
        )
    }

    @ParameterizedTest
    @ValueSource(ints = [10, 11, 22])
    fun `Floor decoration location strategy`(type: Int) {
        // Given
        val target: Location = mockk(relaxed = true)
        every { target.type } returns type
        // When
        val strategy = pf.getStrategy(target)
        // Then
        assertEquals(RectangleTargetStrategy(collisions, target.tile, target.size, target.def.blockFlag), strategy)
    }

    @ParameterizedTest
    @ValueSource(ints = [12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 23, 24])
    fun `Other location strategy`(type: Int) {
        // Given
        val target: Location = mockk(relaxed = true)
        every { target.type } returns type
        // When
        val strategy = pf.getStrategy(target)
        // Then
        assertEquals(TileTargetStrategy(target.tile), strategy)
    }

    @Test
    fun `Other strategy`() {
        // Given
        val target: Entity = mockk(relaxed = true)
        // When
        val strategy = pf.getStrategy(target)
        // Then
        assertEquals(TileTargetStrategy(target.tile), strategy)
    }

}