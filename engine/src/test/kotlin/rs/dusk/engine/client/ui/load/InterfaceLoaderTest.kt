package rs.dusk.engine.client.ui.load

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rs.dusk.engine.client.ui.detail.InterfaceData
import rs.dusk.engine.client.ui.detail.InterfaceDetail
import rs.dusk.engine.client.ui.detail.InterfaceDetails
import rs.dusk.engine.client.ui.detail.InterfaceDetailsLoader
import rs.dusk.engine.data.file.FileLoader

internal class InterfaceLoaderTest {

    private lateinit var loader: InterfaceDetailsLoader
    private lateinit var fileLoader: FileLoader

    @BeforeEach
    fun setup() {
        fileLoader = mockk()
        loader = spyk(InterfaceDetailsLoader(fileLoader))
    }

    @Test
    fun `Load details`() {
        val raw = mapOf("interface_name" to mapOf("id" to 1, "type" to "interface_type", "components" to mapOf("component_name" to 0)))
        val data = InterfaceData()
        val types = mapOf("interface_type" to data)
        val results = loader.loadDetails(raw, types)
        val expected = mapOf(1 to InterfaceDetail(id = 1, name = "interface_name", type = "interface_type", data = data, components = mapOf(0 to "component_name")))
        assertEquals(expected, results)
    }

    @Test
    fun `Load multiple details`() {
        val raw = mapOf(
            "interface_name" to mapOf("id" to 1, "type" to "interface_type", "components" to mapOf("component_name" to 1, "component_name_two" to 2)),
            "interface_name_two" to mapOf("id" to 2, "type" to "interface_type", "components" to mapOf("component_name" to 3))
        )
        val data = InterfaceData()
        val types = mapOf("interface_type" to data)
        val results = loader.loadDetails(raw, types)
        val expected = mapOf(
            1 to InterfaceDetail(id = 1, name = "interface_name", type = "interface_type", data = data, components = mapOf(1 to "component_name", 2 to "component_name_two")),
            2 to InterfaceDetail(id = 2, name = "interface_name_two", type = "interface_type", data = data, components = mapOf(3 to "component_name"))
        )
        assertEquals(expected, results)
    }

    @Test
    fun `Missing id throws exception`() {
        val raw = mapOf("interface_name" to mapOf("type" to "interface_type"))
        val data = InterfaceData()
        val types = mapOf("interface_type" to data)
        assertThrows<IllegalStateException> {
            loader.loadDetails(raw, types)
        }
    }

    @Test
    fun `Missing type throws exception`() {
        val raw = mapOf("interface_name" to mapOf("id" to 1, "type" to "interface_type"))
        assertThrows<IllegalStateException> {
            loader.loadDetails(raw, mapOf())
        }
    }

    @Test
    fun `No type defaults to main screen`() {
        val raw = mapOf("interface_name" to mapOf("id" to 1))
        val data = InterfaceData()
        val types = mapOf("main_screen" to data)
        val results = loader.loadDetails(raw, types)
        val expected = mapOf(1 to InterfaceDetail(id = 1, name = "interface_name", type = "main_screen", data = data))
        assertEquals(expected, results)
    }

    @Test
    fun `Missing components is empty map`() {
        val raw = mapOf("interface_name" to mapOf("id" to 1, "type" to "interface_type", "components" to mapOf<String, Int>()))
        val data = InterfaceData()
        val types = mapOf("interface_type" to data)
        val results = loader.loadDetails(raw, types)
        val expected = mapOf(1 to InterfaceDetail(id = 1, name = "interface_name", type = "interface_type", data = data, components = mapOf()))
        assertEquals(expected, results)
    }

    @Test
    fun `No components is empty map`() {
        val raw = mapOf("interface_name" to mapOf("id" to 1, "type" to "interface_type"))
        val data = InterfaceData()
        val types = mapOf("interface_type" to data)
        val results = loader.loadDetails(raw, types)
        val expected = mapOf(1 to InterfaceDetail(id = 1, name = "interface_name", type = "interface_type", data = data, components = mapOf()))
        assertEquals(expected, results)
    }

    @Test
    fun `Load from file`() {
        val path = "dusk/example/interfaces.yml"
        val raw = mapOf("interface_name" to mapOf("id" to 1, "type" to "interface_type"))
        every { fileLoader.load<Map<String, Map<String, Any>>>(path) } returns raw
        val result = loader.loadFile(path)
        assertEquals(raw, result)
    }

    @Test
    fun `Load all`() {
        val detailsPath = "interfaces.yml"
        val typesPath = "interface-types.yml"
        val detailData = mapOf(
            "interface_name" to mapOf(
                "id" to 1,
                "type" to "interface_type",
                "components" to mapOf("component_name" to 1)
            ),
            "toplevel" to mapOf("id" to 2, "type" to "root"),
            "toplevel_full" to mapOf("id" to 3, "type" to "root"),
            "root" to mapOf("id" to -1, "type" to "root")
        )
        val typesData = mapOf(
            "interface_type" to mapOf("index" to 0),
            "root" to mapOf("index" to 0, "parent" to "root")
        )
        every { fileLoader.load<Map<String, Map<String, Any>>>(detailsPath) } returns detailData
        every { fileLoader.load<Map<String, Map<String, Any>>>(typesPath) } returns typesData
        val result = loader.loadAll(detailsPath, typesPath)
        val expected = InterfaceDetails(
            mapOf(
                1 to InterfaceDetail(
                    id = 1,
                    name = "interface_name",
                    type = "interface_type",
                    data = InterfaceData(2, 3, 0, 0),
                    components = mapOf(1 to "component_name")
                ),
                2 to InterfaceDetail(id = 2, name = "toplevel", type = "root", data = InterfaceData(-1, -1, 0, 0)),
                3 to InterfaceDetail(id = 3, name = "toplevel_full", type = "root", data = InterfaceData(-1, -1, 0, 0)),
                -1 to InterfaceDetail(id = -1, name = "root", type = "root", data = InterfaceData(-1, -1, 0, 0))
            ),
            mapOf(
                "interface_name" to 1,
                "toplevel" to 2,
                "toplevel_full" to 3,
                "root" to -1
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `Load parameters`() {
        every { loader.loadAll(any(), any()) } returns mockk()
        loader.run("one", "two")
        verify { loader.loadAll("one", "two") }
    }
}
