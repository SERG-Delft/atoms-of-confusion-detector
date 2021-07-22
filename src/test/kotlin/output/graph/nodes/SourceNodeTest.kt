package output.graph.nodes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import output.exceptions.NotANeighbourException
import output.exceptions.SameTypeOfNodeAsNeighbourException
import output.graph.Edge

internal class SourceNodeTest {
    private val atomName = "atomName"
    private val sourceName = "sourceName"

    @Test
    fun testAddNeighbour() {
        val atomNode = AtomNode(atomName)
        val sourceNode = SourceNode(sourceName)
        val edge = Edge(mutableSetOf<Int>(1, 20, 34), atomNode, sourceNode)
        val expectedEdge = Edge(mutableSetOf<Int>(1, 20, 34), atomNode, sourceNode)
        sourceNode.addNeighbour(atomNode, edge)
        assertNotNull(sourceNode.edgeMap[atomNode])
        assertEquals(expectedEdge, sourceNode.edgeMap[atomNode])
    }

    @Test
    fun testAddNeighbourSourceNode() {
        val atomNode = AtomNode(atomName)
        val sourceNode = SourceNode(sourceName)
        assertThrows<SameTypeOfNodeAsNeighbourException> {
            val edge = Edge(mutableSetOf<Int>(1, 20, 34), atomNode, sourceNode)
            sourceNode.addNeighbour(sourceNode, edge)
        }
    }

    @Test
    fun testGetEdgeToNeighbour() {
        val atomNode = AtomNode(atomName)
        val sourceNode = SourceNode(sourceName)
        val edge = Edge(mutableSetOf<Int>(1, 20, 34), atomNode, sourceNode)
        val expectedEdge = Edge(mutableSetOf<Int>(1, 20, 34), atomNode, sourceNode)
        sourceNode.addNeighbour(atomNode, edge)
        assertEquals(expectedEdge, sourceNode.getEdgeToNeighbour(atomNode))
    }

    @Test
    fun testGetEdgeToNeighbourNotANeighbour() {
        val atomNode = AtomNode(atomName)
        val sourceNode = SourceNode(sourceName)
        assertThrows<NotANeighbourException> {
            sourceNode.getEdgeToNeighbour(atomNode)
        }
    }
}
