package github

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import output.Atom
import output.graph.ConfusionGraph
import parsing.ParsedFile

@Suppress("UnusedPrivateMember")
internal class PRDeltaTest {

    private val sources = listOf("F1.java", "F2.java")
    private val sourceGraph = ConfusionGraph(sources)
    private val targetGraph = ConfusionGraph(sources)
    private val parsedF1 = mockk<ParsedFile>()
    private val parsedF2 = mockk<ParsedFile>()
    private val diffParser = mockk<DiffParser>()
    lateinit var prDelta: PRDelta

    @BeforeEach
    fun setup() {
        every { parsedF1.name } returns "F1.java"
        every { parsedF2.name } returns "F2.java"
        sourceGraph.addAppearancesOfAtom(Atom.INDENTATION, "F1.java", mutableSetOf(20, 32))
        sourceGraph.addAppearancesOfAtom(Atom.INDENTATION, "F2.java", mutableSetOf(42))
        targetGraph.addAppearancesOfAtom(Atom.INDENTATION, "F1.java", mutableSetOf(32))
        targetGraph.addAppearancesOfAtom(Atom.INDENTATION, "F2.java", mutableSetOf(42, 75))
        targetGraph.addAppearancesOfAtom(Atom.TYPE_CONVERSION, "F2.java", mutableSetOf(20))

        every { diffParser.removedLinesForFile("F1.java") } returns mutableSetOf(20)
        every { diffParser.addedLinesForFile("F1.java") } returns mutableSetOf()
        every { diffParser.addedLinesForFile("F2.java") } returns mutableSetOf(20, 70, 71, 75, 120)
        every { diffParser.removedLinesForFile("F2.java") } returns mutableSetOf(20, 70, 71, 75, 120)
        prDelta = PRDelta(sourceGraph, targetGraph, sources, sources, diffParser)
    }

    @Test
    fun testGetRemovedAtoms() {
        val expected = mutableListOf("INDENTATION, F1.java, 20")
        // the next line mapping helps the assertion between lists work
        val actual = prDelta.getRemovedAtoms().map { it -> "${it[0]}, ${it[1]}, ${it[2]}" }
        // assertEquals(expected, actual)
    }

    @Test
    fun testGetAddedAtoms() {
        val expected = mutableListOf(
            "INDENTATION, F2.java, 75",
            "TYPE_CONVERSION, F2.java, 20"
        )
        // the next line mapping helps the assertion between lists work
        val actual = prDelta.getAddedAtoms().map { it -> "${it[0]}, ${it[1]}, ${it[2]}" }
        // assertEquals(expected, actual)
    }

    @Test
    fun testGetRemainingAtoms() {
        val expected = mutableListOf(
            "INDENTATION, F1.java, 32",
            "INDENTATION, F2.java, 42"
        )
        // the next line mapping helps the assertion between lists work
        val actual = prDelta.getRemainingAtoms().map { it -> "${it[0]}, ${it[1]}, ${it[2]}" }
        // assertEquals(expected, actual)
    }
}
