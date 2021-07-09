package output.graph.nodes

import output.graph.Edge

class AtomNode(override val name: String) : ConfusionGraphNode(name) {

    override fun addNeighbour(node: ConfusionGraphNode) {
        TODO("Not yet implemented")
    }

    override fun getEdgeToNeighbour(node: ConfusionGraphNode): Edge {
        TODO("Not yet implemented")
    }
}
