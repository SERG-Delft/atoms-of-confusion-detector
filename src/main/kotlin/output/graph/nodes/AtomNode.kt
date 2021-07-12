package output.graph.nodes

import output.exceptions.NotANeighbourException
import output.exceptions.SameTypeOfNodeAsNeighbourException
import output.graph.Edge

class AtomNode(override val name: String) : ConfusionGraphNode(name) {

    val edgeMap = mutableMapOf<SourceNode, Edge>()

    override fun addNeighbour(node: ConfusionGraphNode, edge: Edge) {
        if (node !is SourceNode) throw SameTypeOfNodeAsNeighbourException()
        edgeMap[node] = edge
    }

    override fun getEdgeToNeighbour(node: ConfusionGraphNode): Edge {
        if (!edgeMap.containsKey(node)) throw NotANeighbourException(this, node)
        return edgeMap[node]!!
    }
}
