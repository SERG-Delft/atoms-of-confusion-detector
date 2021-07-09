package output.graph.nodes

import output.exceptions.NotANeighbourException
import output.exceptions.SameTypeOfNodeAsNeighbourException
import output.graph.Edge
import kotlin.jvm.Throws

abstract class ConfusionGraphNode(open val name: String) {

    @Throws(SameTypeOfNodeAsNeighbourException::class)
    abstract fun addNeighbour(node: ConfusionGraphNode)

    @Throws(NotANeighbourException::class)
    abstract fun getEdgeToNeighbour(node: ConfusionGraphNode): Edge
}
