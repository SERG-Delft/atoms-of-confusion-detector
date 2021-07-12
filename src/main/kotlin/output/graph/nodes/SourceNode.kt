package output.graph.nodes

import output.exceptions.NotANeighbourException
import output.exceptions.SameTypeOfNodeAsNeighbourException
import output.graph.Edge

class SourceNode(name: String) : ConfusionGraphNode(name) {

    val edgeMap = mutableMapOf<AtomNode, Edge>()

    override fun addNeighbour(node: ConfusionGraphNode, edge: Edge) {
        if (node !is AtomNode) throw SameTypeOfNodeAsNeighbourException()
        edgeMap[node] = edge
    }

    override fun getEdgeToNeighbour(node: ConfusionGraphNode): Edge {
        if (!edgeMap.containsKey(node)) throw NotANeighbourException(this, node)
        return edgeMap[node]!!
    }
}
