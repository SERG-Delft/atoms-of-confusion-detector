package output.graph.nodes

import output.exceptions.NotANeighbourException
import output.exceptions.SameTypeOfNodeAsNeighbourException
import output.graph.Edge

class SourceNode(name: String) : ConfusionGraphNode(name) {

    val edgeMap = mutableMapOf<AtomNode, Edge>()

    override fun addNeighbour(node: ConfusionGraphNode, edge: Edge) {
        if (node !is AtomNode) throw SameTypeOfNodeAsNeighbourException()
        edgeMap[node] = edge
        node.edgeMap[this] = edge
    }

    override fun getEdgeToNeighbour(node: ConfusionGraphNode): Edge {
        if (!edgeMap.containsKey(node)) throw NotANeighbourException(this, node)
        return edgeMap[node]!!
    }

    override fun hasNeighbour(node: ConfusionGraphNode): Boolean {
        if (node is SourceNode) throw SameTypeOfNodeAsNeighbourException()
        return edgeMap.containsKey(node)
    }

    override fun toString(): String {
        return "SourceNode($name)"
    }
}
