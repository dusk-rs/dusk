package rs.dusk.engine.model.entity.index.player.data.interfaces

import com.scalified.tree.multinode.ArrayMultiTreeNode

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since June 25, 2020
 */
@Suppress("UnstableApiUsage")
class InterfaceRepository {

    /**
     * The topmost node
     */
    private var parentNode: ArrayMultiTreeNode<Int>? = null

    /**
     * Adds an interface to the head of the tree
     */
    fun storeWindow(interfaceId: Int) {
        // the collection is either empty and a topmost node can simply be added, or the current topmost node's value must be swapped with the new node
        if (parentNode == null) {
            parentNode = ArrayMultiTreeNode(interfaceId)
        } else {
            parentNode!!.setData(interfaceId)
        }
    }

    /**
     * Add an interface to the [collection of interfaces][interfaces]
     * @param parentId The parent interface id, if this is null then it must be a root node. If an attempt
     */
    fun storeInterface(parentId: Int? = null, interfaceId: Int) {
        if (parentNode == null) {
            throw IllegalStateException("Unable to store an interface in the collection - there is no parent node yet [fix: the window must be drawn before interfaces are sent]")
        }
        // store the interface as a child of the parent
        if (parentId == null) {
            parentNode!!.add(ArrayMultiTreeNode(interfaceId))
        } else {
            val parentNode = parentNode!!.find(parentId)
                ?: throw IllegalStateException("Unable to find the parent node of an interface")
            parentNode.add(ArrayMultiTreeNode(interfaceId))
        }
    }

    /**
     * Get the interface id of the topmost window
     */
    fun getWindowInterfaceId(): Int {
        return if (parentNode != null) parentNode!!.data() else throw IllegalStateException("Unable to get topmost node data - it did not exist")
    }

}


