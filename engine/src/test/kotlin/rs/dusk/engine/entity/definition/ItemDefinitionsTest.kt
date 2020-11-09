package rs.dusk.engine.entity.definition

import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import rs.dusk.cache.definition.data.ItemDefinition
import rs.dusk.cache.definition.decoder.ItemDecoder
import rs.dusk.engine.TimedLoader
import rs.dusk.engine.data.file.FileLoader
import rs.dusk.engine.entity.definition.load.ItemDefinitionLoader
import rs.dusk.engine.entity.item.EquipSlot
import rs.dusk.engine.entity.item.EquipType
import rs.dusk.engine.entity.item.ItemKept

internal class ItemDefinitionsTest : DefinitionsDecoderTest<ItemDefinition, ItemDecoder, ItemDefinitions>() {

    @BeforeEach
    override fun setup() {
        decoder = mockk(relaxed = true)
        super.setup()
    }

    override fun map(id: Int): Map<String, Any> {
        return mapOf(
            "id" to id,
            "slot" to "Hands",
            "type" to "Sleeveless",
            "weight" to 1.01,
            "edible" to true,
            "tradeable" to false,
            "alchable" to false,
            "bankable" to false,
            "individual" to true,
            "limit" to 100,
            "kept" to "Wilderness",
            "destroy" to "No going back",
            "examine" to "Floating hands"
        )
    }

    override fun populated(id: Int): Map<String, Any> {
        return mapOf(
            "id" to id,
            "slot" to EquipSlot.Hands,
            "type" to EquipType.Sleeveless,
            "weight" to 1.01,
            "edible" to true,
            "tradeable" to false,
            "alchable" to false,
            "bankable" to false,
            "individual" to true,
            "limit" to 100,
            "kept" to ItemKept.Wilderness,
            "destroy" to "No going back",
            "examine" to "Floating hands",
            "equip" to -1
        )
    }

    override fun definition(id: Int): ItemDefinition {
        return ItemDefinition(id)
    }

    override fun definitions(decoder: ItemDecoder, id: Map<String, Map<String, Any>>, names: Map<Int, String>): ItemDefinitions {
        return ItemDefinitions(decoder, id, names)
    }

    override fun loader(loader: FileLoader): TimedLoader<ItemDefinitions> {
        return ItemDefinitionLoader(loader, decoder)
    }

}