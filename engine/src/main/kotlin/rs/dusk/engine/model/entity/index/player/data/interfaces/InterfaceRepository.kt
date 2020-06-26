package rs.dusk.engine.model.entity.index.player.data.interfaces

import com.google.common.graph.GraphBuilder

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since June 25, 2020
 */
@Suppress("UnstableApiUsage")
class InterfaceRepository {

    private val interfaces = GraphBuilder.directed().build<InterfaceDisplayed>()

    /**
     * Adds an interface to the head of the tree
     */
    fun addTopInterface(interfaceId: Int, index: Int): Boolean = with(interfaces) {
        // remove entire collection if exists
        if (interfaces.nodes().isNotEmpty()) {
            clear()
        }
        return addNode(InterfaceDisplayed(interfaceId, index))
    }

    fun addInterface(parentId: Int? = null, interfaceId: Int, index: Int): Boolean = with(interfaces) {
        // no parent, top-most node
        val parent = getParentInterface(parentId ?: interfaceId)
        return if (parent == null || parentId == null) {
            addNode(InterfaceDisplayed(interfaceId, index))
        } else {
            putEdge(parent, InterfaceDisplayed(interfaceId, index))
        }
    }

    /**
     * Finds the parent [interface][InterfaceDisplayed]
     * @param interfaceId The id of the interface we wish to find a parent of
     */
    fun getParentInterface(interfaceId: Int): InterfaceDisplayed? = with(interfaces) {
        val interfaceNode = getInterfaceNode(interfaceId)
            ?: throw IllegalStateException("Unable to find the node to which the interface belongs [interfaceId=$interfaceId]")

        val parents = predecessors(interfaceNode)
        if (parents == null || parents.isEmpty()) {
            return null
        }
        return parents.first()
    }

    fun getChildInterfaces(interfaceId: Int): MutableSet<InterfaceDisplayed>? = with(interfaces) {
        val interfaceNode = getInterfaceNode(interfaceId)
            ?: throw IllegalStateException("Unable to find the node to which the interface belongs [interfaceId=$interfaceId]")
        val children = successors(interfaceNode)
        if (children == null || children.isEmpty()) {
            return mutableSetOf()
        }
        return children
    }

    fun getInterfaceNode(interfaceId: Int): InterfaceDisplayed? = with(interfaces) {
        var interfaceNode: InterfaceDisplayed? = null
        for (node in nodes()) {
            if (node.interfaceId == interfaceId) {
                interfaceNode = node
                break
            }
        }
        return interfaceNode
    }

    /**
     * Gets the window, which is the topmost interface
     */
    fun getWindow(): InterfaceDisplayed? = with(interfaces) {
        return if (nodes().isEmpty()) {
            null
        } else {
            nodes().first()
        }
    }

    fun clear() = with(interfaces) {
        nodes().forEach { node ->
            removeNode(node)
        }
    }

}


