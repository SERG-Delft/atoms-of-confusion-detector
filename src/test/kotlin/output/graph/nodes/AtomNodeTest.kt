package output.graph.nodes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import output.exceptions.NotANeighbourException
import output.exceptions.SameTypeOfNodeAsNeighbourException
import output.graph.Edge

internal class AtomNodeTest {

    private val atomName = "atomName"
    private val sourceName = "sourceName"

    @Test
    fun testAddNeighbour() {
        val atomNode = AtomNode(atomName)
        val sourceNode = SourceNode(sourceName)
        val edge = Edge(mutableSetOf<Int>(1, 20, 34), atomNode, sourceNode)
        val expectedEdge = Edge(mutableSetOf<Int>(1, 20, 34), atomNode, sourceNode)
        atomNode.addNeighbour(sourceNode, edge)
        assertNotNull(atomNode.edgeMap[sourceNode])
        assertEquals(expectedEdge, atomNode.edgeMap[sourceNode])
    }

    @Test
    fun testAddNeighbourAtomNode() {
        val atomNode = AtomNode(atomName)
        val sourceNode = SourceNode(sourceName)
        assertThrows<SameTypeOfNodeAsNeighbourException> {
            val edge = Edge(mutableSetOf<Int>(1, 20, 34), atomNode, sourceNode)
            atomNode.addNeighbour(atomNode, edge)
        }
    }

    @Test
    fun testGetEdgeToNeighbour() {
        val atomNode = AtomNode(atomName)
        val sourceNode = SourceNode(sourceName)
        val edge = Edge(mutableSetOf<Int>(1, 20, 34), atomNode, sourceNode)
        val expectedEdge = Edge(mutableSetOf<Int>(1, 20, 34), atomNode, sourceNode)
        atomNode.addNeighbour(sourceNode, edge)
        assertEquals(expectedEdge, atomNode.getEdgeToNeighbour(sourceNode))
    }

    @Test
    fun testGetEdgeToNeighbourNotANeighbour() {
        val atomNode = AtomNode(atomName)
        val sourceNode = SourceNode(sourceName)
        assertThrows<NotANeighbourException> {
            atomNode.getEdgeToNeighbour(sourceNode)
        }
    }
}
