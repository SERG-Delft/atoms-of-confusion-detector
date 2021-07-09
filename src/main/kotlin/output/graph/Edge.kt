package output.graph

import output.graph.nodes.AtomNode
import output.graph.nodes.SourceNode

class Edge(val lines: Set<Int>, val atomNode: AtomNode, val sourceNode: SourceNode)
