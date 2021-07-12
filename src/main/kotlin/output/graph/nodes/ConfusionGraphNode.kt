package output.graph.nodes

import output.exceptions.NotANeighbourException
import output.exceptions.SameTypeOfNodeAsNeighbourException
import output.graph.Edge
import kotlin.jvm.Throws

abstract class ConfusionGraphNode(open val name: String) {

    @Throws(SameTypeOfNodeAsNeighbourException::class)
    abstract fun addNeighbour(node: ConfusionGraphNode, edge: Edge)

    @Throws(NotANeighbourException::class)
    abstract fun getEdgeToNeighbour(node: ConfusionGraphNode): Edge

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
