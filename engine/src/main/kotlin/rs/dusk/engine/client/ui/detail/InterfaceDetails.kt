package rs.dusk.engine.client.ui.detail

import rs.dusk.engine.client.ui.InterfaceException

data class InterfaceDetails(
    private val interfaces: Map<Int, InterfaceDetail>,
    private val names: Map<String, Int>
) {

    fun get(id: Int) = interfaces[id] ?: InterfaceDetail(id)

    fun get(name: String) = get(getId(name))

    fun getSafe(name: String) = get(getIdOrNull(name) ?: INVALID_ID)

    fun get(name: String, component: String, block: (InterfaceDetail, Int) -> Boolean): Boolean {
        val inter = getSafe(name)
        val componentId = inter.getComponent(component) ?: return false
        return block.invoke(inter, componentId)
    }

    fun get(id: Int, component: String, block: (InterfaceDetail, Int) -> Boolean): Boolean {
        val inter = get(id)
        val componentId = inter.getComponent(component) ?: return false
        return block.invoke(inter, componentId)
    }

    fun get(id: Int, component: String, block: (Int) -> Boolean): Boolean {
        val inter = get(id)
        val componentId = inter.getComponent(component) ?: return false
        return block.invoke(componentId)
    }

    fun getId(name: String) = getIdOrNull(name) ?: throw IllegalNameException(
        name
    )

    fun getIdOrNull(name: String) = names[name]

    val size: Int = interfaces.count()

    class IllegalNameException(name: String) : InterfaceException(name)

    companion object {
        private const val INVALID_ID = -1
    }

}