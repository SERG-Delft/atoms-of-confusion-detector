package parsing.detectors

import org.antlr.v4.runtime.CharStreams
import org.junit.jupiter.api.Test
import output.graph.ConfusionGraph
import parsing.AtomsVisitor
import parsing.ParsedFile
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class InfixPrecedenceDetectorTest {

    private fun parse(code: String): Triple<AtomsVisitor, ConfusionGraph, ParsedFile> {

        // set up visitor, graph and detector
        val v = AtomsVisitor(); v.fileName = "f1"
        val g = ConfusionGraph(listOf("f1"))
        val d = InfixPrecedenceDetector(v, g)

        // register detector
        v.registerDetector(d)

        val file = ParsedFile(CharStreams.fromString(code))

        return Triple(v, g, file)
    }

    private fun runVisitorExpr(code: String): List<List<Any>> {
        val (v, g, file) = parse(code)
        file.parser.expression().accept(v)
        return g.getAllAtomAppearances()
    }

    @Test
    fun testPresent() {
        val atoms = runVisitorExpr("1 + 1/2")

        assertEquals(1, atoms.size)
        assertEquals("INFIX_OPERATOR_PRECEDENCE", atoms[0][0])
    }

    @Test
    fun testInstanceof() {
        val atoms = runVisitorExpr("1 * 2 instanceof String")

        assertEquals(1, atoms.size)
        assertEquals("INFIX_OPERATOR_PRECEDENCE", atoms[0][0])
    }

    @Test
    fun testBitshift() {
        val atoms = runVisitorExpr("1 * 2 >>> 1")

        assertEquals(1, atoms.size)
        assertEquals("INFIX_OPERATOR_PRECEDENCE", atoms[0][0])
    }

    @Test
    fun testAbsent() {
        val atoms = runVisitorExpr("1 + (1/2)")
        assertTrue(atoms.isEmpty())
    }
}
