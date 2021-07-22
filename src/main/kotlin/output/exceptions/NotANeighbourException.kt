package output.exceptions

import output.graph.nodes.ConfusionGraphNode

class NotANeighbourException(private val startingNode: ConfusionGraphNode, private val endNode: ConfusionGraphNode) :
    Throwable("${endNode.name} is not a neighbour of ${startingNode.name}")
