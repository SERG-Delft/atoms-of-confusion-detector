package output.graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import output.Atom
import output.exceptions.SourceDoesNotExistException
import output.graph.nodes.AtomNode
import output.graph.nodes.SourceNode

internal class ConfusionGraphTest {

    private val sourceName = "Source.java"
    private val lines = mutableSetOf(20, 55, 67)

    @Test
    fun testConstructorSourceNodes() {
        val expectedSourceNodes = mapOf(sourceName to SourceNode(sourceName))
        val graph = ConfusionGraph(listOf(sourceName))
        assertEquals(expectedSourceNodes, graph.sourceNodes)
    }

    @Test
    fun testAddAppearancesOfAtomSourceExists() {
        val g1 = ConfusionGraph(listOf(sourceName))
        g1.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, sourceName, lines)
        assertEquals(
            Edge(mutableSetOf(20, 55, 67), AtomNode(Atom.CHANGE_OF_LITERAL_ENCODING.name), SourceNode(sourceName)),
            g1.atomNodes[Atom.CHANGE_OF_LITERAL_ENCODING.name]!!.edgeMap[SourceNode(sourceName)]
        )
    }

    @Test
    fun testAddAppearancesOfAtomSourceDoesNotExist() {
        val g1 = ConfusionGraph(emptyList<String>())
        g1.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, sourceName, lines)
        assertEquals(
            Edge(mutableSetOf(20, 55, 67), AtomNode(Atom.CHANGE_OF_LITERAL_ENCODING.name), SourceNode(sourceName)),
            g1.atomNodes[Atom.CHANGE_OF_LITERAL_ENCODING.name]!!.edgeMap[SourceNode(sourceName)]
        )
    }

    @Test
    fun testAddAppearanceOfAtomEdgeExists() {
        val g1 = ConfusionGraph(emptyList<String>())

        val expected = Edge(
            mutableSetOf(20, 55, 67, 13, 45, 78),
            AtomNode(Atom.CHANGE_OF_LITERAL_ENCODING.name),
            SourceNode(sourceName)
        )
        g1.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, sourceName, lines)
        g1.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, sourceName, mutableSetOf(13, 45, 78))
        assertEquals(
            expected,
            g1.atomNodes[Atom.CHANGE_OF_LITERAL_ENCODING.name]!!.edgeMap[SourceNode(sourceName)]
        )
        assertEquals(
            expected,
            g1.sourceNodes[sourceName]!!.edgeMap[AtomNode(Atom.CHANGE_OF_LITERAL_ENCODING.name)]
        )
    }

    @Test
    fun testCountLineAppearancesOfAtom() {
        val otherSource = "OtherSource.java"
        val g1 = ConfusionGraph(listOf(sourceName, otherSource))
        g1.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, sourceName, lines)
        g1.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, otherSource, lines)
        val result = g1.countLineAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING)
        assertEquals(6, result)
    }

    @Test
    fun testCountFileAppearancesOfAtom() {
        val otherSource = "OtherSource.java"
        val g1 = ConfusionGraph(listOf(sourceName, otherSource))
        g1.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, sourceName, lines)
        g1.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, otherSource, lines)
        val result = g1.countFileAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING)
        assertEquals(2, result)
    }

    @Test
    fun testFindAppearancesOfAtom() {
        val otherSource = "OtherSource.java"
        val g1 = ConfusionGraph(listOf(sourceName, otherSource))
        g1.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, sourceName, lines)
        g1.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, otherSource, mutableSetOf(30, 42, 56))
        val expected = listOf(Pair(sourceName, setOf(20, 55, 67)), Pair(otherSource, setOf(30, 42, 56)))
        val result = g1.findAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING)
        assertEquals(expected, result)
    }

    @Test
    fun testFindAtomsInSource() {
        val g1 = ConfusionGraph(listOf(sourceName))
        g1.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, sourceName, lines)
        g1.addAppearancesOfAtom(Atom.PRE_INCREMENT_DECREMENT, sourceName, mutableSetOf(30, 42, 56))
        val expected = listOf(
            Pair(Atom.CHANGE_OF_LITERAL_ENCODING.name, setOf(20, 55, 67)),
            Pair(Atom.PRE_INCREMENT_DECREMENT.name, setOf(30, 42, 56))
        )
        val result = g1.findAtomsInSource(sourceName)
        assertEquals(expected, result)
    }

    @Test
    fun testFindAtomsInSourceSourceDoesNotExist() {
        val g1 = ConfusionGraph(listOf(sourceName))
        assertThrows<SourceDoesNotExistException> {
            g1.findAtomsInSource("OtherSource.java")
        }
    }
}
