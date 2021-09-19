package output.graph.nodes

import output.exceptions.NotANeighbourException
import output.exceptions.SameTypeOfNodeAsNeighbourException
import output.graph.Edge

class SourceNode(name: String) : ConfusionGraphNode(name) {

    val edgeMap = mutableMapOf<AtomNode, Edge>()

    /**
     * Adds a neighbor to this node.
     *
     * @param node the neighboring node.
     * @param edge the edge that connects the 2 nodes.
     * @throws SameTypeOfNodeAsNeighbourException iff the nodes are
     *         of the same type (e.g. Source Node neighboring a Source Node).
     */
    override fun addNeighbour(node: ConfusionGraphNode, edge: Edge) {
        if (node !is AtomNode) throw SameTypeOfNodeAsNeighbourException()
        edgeMap[node] = edge
        node.edgeMap[this] = edge
    }

    /**
     * Return the edge between this node and a specific neighbor.
     *
     * @param node the neighboring node.
     * @throws NotANeighbourException iff the specified node is not a neighbor of this node.
     * @return the edge between the 2 nodes.
     */
    override fun getEdgeToNeighbour(node: ConfusionGraphNode): Edge {
        if (!edgeMap.containsKey(node)) throw NotANeighbourException(this, node)
        return edgeMap[node]!!
    }

    /**
     * Checks if this node has another given node as a neighbor.
     *
     * @param node the node to see if it's a neighbor
     * @throws SameTypeOfNodeAsNeighbourException iff the nodes are
     *         of the same type (e.g. Source Node neighboring a Source Node).
     * @return true iff the nodes are neighbors else false
     */
    override fun hasNeighbour(node: ConfusionGraphNode): Boolean {
        if (node is SourceNode) throw SameTypeOfNodeAsNeighbourException()
        return edgeMap.containsKey(node)
    }

    override fun toString(): String {
        return "SourceNode($name)"
    }
}
