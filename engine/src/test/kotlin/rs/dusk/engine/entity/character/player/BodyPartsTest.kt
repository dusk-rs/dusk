package rs.dusk.engine.entity.character.player

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import rs.dusk.cache.definition.data.ItemDefinition
import rs.dusk.cache.definition.decoder.ItemDecoder
import rs.dusk.engine.entity.character.contain.Container
import rs.dusk.engine.entity.item.BodyPart
import rs.dusk.engine.entity.item.EquipType
import rs.dusk.engine.entity.item.detail.ItemDetail
import rs.dusk.engine.entity.item.detail.ItemDetails

@ExtendWith(MockKExtension::class)
internal class BodyPartsTest {

    @MockK
    lateinit var equipment: Container

    lateinit var looks: IntArray

    @MockK
    lateinit var details: ItemDetails

    @MockK
    lateinit var decoder: ItemDecoder

    lateinit var body: BodyParts

    @BeforeEach
    fun setup() {
        looks = IntArray(12)
        body = BodyParts(equipment, looks, details, decoder)
    }

    @Test
    fun `Get body part value`() {
        assertEquals(0, body.get(4))
    }

    @Test
    fun `Get out of bounds body part value`() {
        assertEquals(-1, body.get(13))
    }

    @Test
    fun `Update item`() {
        every { equipment.getItem(1) } returns 123
        val detail: ItemDetail = mockk()
        every { details.get(123) } returns detail
        every { detail.equip } returns 2
        body.update(BodyPart.Cape)
        assertEquals(2 or 0x8000, body.get(1))
    }

    @Test
    fun `Update missing item defaults to body part`() {
        looks[2] = 321
        every { equipment.getItem(4) } returns -1
        body.update(BodyPart.Chest)
        assertEquals(321 or 0x100, body.get(4))
    }

    @Test
    fun `Update missing item and look sets to zero`() {
        every { equipment.getItem(14) } returns 123
        val detail: ItemDetail = mockk()
        every { details.get(123) } returns detail
        every { detail.equip } returns 0
        body.update(BodyPart.Aura)
        every { equipment.getItem(14) } returns -1
        val result = body.update(BodyPart.Aura)
        assertTrue(result)
        assertEquals(0, body.get(12))
    }

    @Test
    fun `Update semi body skips arms`() {
        every { equipment.getItem(4) } returns 123
        val detail: ItemDetail = mockk()
        every { details.get(123) } returns detail
        every { detail.type } returns EquipType.None
        body.update(BodyPart.Arms)
        assertEquals(0, body.get(4))
    }

    @Test
    fun `Update hat skips hair`() {
        every { equipment.getItem(0) } returns 123
        val detail: ItemDetail = mockk()
        every { details.get(123) } returns detail
        every { decoder.getSafe(123) } returns ItemDefinition(name = "bald")
        every { detail.type } returns EquipType.Hair
        body.update(BodyPart.Hat)
        assertEquals(0, body.get(0))
    }

    @Test
    fun `Update beard skips if bald chin`() {
        every { equipment.getItem(-1) } returns -1
        val detail: ItemDetail = mockk()
        every { details.get(123) } returns detail
        every { decoder.getSafe(123) } returns ItemDefinition(name = "bald")
        every { detail.type } returns EquipType.None
        body.update(BodyPart.Beard)
        assertEquals(0, body.get(0))
    }

    @Test
    fun `Update hair skips if hooded cape`() {
        every { equipment.getItem(-1) } returns -1
        every { equipment.getItem(1) } returns 123
        val detail: ItemDetail = mockk()
        every { details.get(123) } returns detail
        every { decoder.getSafe(123) } returns ItemDefinition(name = "bald")
        every { detail.type } returns EquipType.HoodedCape
        body.update(BodyPart.Hair)
        assertEquals(0, body.get(0))
    }

    @Test
    fun `Update chest connected to arms`() {
        val body = spyk(body)
        every { body.update(any()) } returns true
        body.updateConnected(BodyPart.Chest)
        verify {
            body.update(BodyPart.Chest)
            body.update(BodyPart.Arms)
        }
    }

    @Test
    fun `Update hat connected to hair and beard`() {
        val body = spyk(body)
        every { body.update(any()) } returns true
        body.updateConnected(BodyPart.Hat)
        verify {
            body.update(BodyPart.Hair)
            body.update(BodyPart.Beard)
        }
    }

    @Test
    fun `Update cape connected to hat`() {
        val body = spyk(body)
        every { body.update(any()) } returns true
        body.updateConnected(BodyPart.Cape)
        verify {
            body.update(BodyPart.Hat)
        }
    }

    @Test
    fun `Update connected returns if any updated`() {
        val body = spyk(body)
        every { body.update(BodyPart.Hat) } returns false
        every { body.update(BodyPart.Hair) } returns true
        every { body.update(BodyPart.Beard) } returns false
        assertTrue(body.updateConnected(BodyPart.Hat))
    }

}