package output.graph.nodes

import output.exceptions.NotANeighbourException
import output.exceptions.SameTypeOfNodeAsNeighbourException
import output.graph.Edge
import kotlin.jvm.Throws

abstract class ConfusionGraphNode(open val name: String) {

    /**
     * Connects the node to another node through the specified edge.
     *
     * @param node the node to be added as the neighbour.
     * @param edge the edge to connect the two nodes.
     * @throws SameTypeOfNodeAsNeighbourException thrown iff the neighbouring node is the same type as the current node.
     */
    @Throws(SameTypeOfNodeAsNeighbourException::class)
    abstract fun addNeighbour(node: ConfusionGraphNode, edge: Edge)

    /**
     * Gets the edge connecting the 2 nodes.
     *
     * @param node the neighbouring node.
     * @throws NotANeighbourException thrown iff the given node is not a neighbour.
     * @returns the edge connecting the two nodes.
     */
    @Throws(NotANeighbourException::class)
    abstract fun getEdgeToNeighbour(node: ConfusionGraphNode): Edge

    /**
     * Checks whether this node has the passed node as a neighbour.
     * @param node the node to be checked.
     * @return true iff this node has the given node as a neighbour.
     */
    abstract fun hasNeighbour(node: ConfusionGraphNode): Boolean

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConfusionGraphNode

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
