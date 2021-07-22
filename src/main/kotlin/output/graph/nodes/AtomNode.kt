package output.graph.nodes

import output.exceptions.NotANeighbourException
import output.exceptions.SameTypeOfNodeAsNeighbourException
import output.graph.Edge

class AtomNode(override val name: String) : ConfusionGraphNode(name) {

    val edgeMap = mutableMapOf<SourceNode, Edge>()

    override fun addNeighbour(node: ConfusionGraphNode, edge: Edge) {
        if (node !is SourceNode) throw SameTypeOfNodeAsNeighbourException()
        edgeMap[node] = edge
        node.edgeMap[this] = edge
    }

    override fun getEdgeToNeighbour(node: ConfusionGraphNode): Edge {
        if (!edgeMap.containsKey(node)) throw NotANeighbourException(this, node)
        return edgeMap[node]!!
    }

    override fun hasNeighbour(node: ConfusionGraphNode): Boolean {
        if (node is AtomNode) throw SameTypeOfNodeAsNeighbourException()
        return edgeMap.containsKey(node)
    }

    override fun toString(): String {
        return "AtomNode($name)"
    }
}
