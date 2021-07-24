package parsing.detectors

import JavaParser
import org.antlr.v4.runtime.CharStreams
import org.junit.jupiter.api.Test
import output.graph.ConfusionGraph
import parsing.AtomsVisitor
import parsing.ParsedFile
import kotlin.test.assertEquals

internal class LogicAsControlFlowDetectorTest {

    private fun parse(code: String): Triple<AtomsVisitor, ConfusionGraph, ParsedFile> {

        // set up visitor, graph and detector
        val v = AtomsVisitor(); v.fileName = "f1"
        val g = ConfusionGraph(listOf("f1"))
        val d = LogicAsControlFlowDetector(v, g)

        // register detector
        v.registerDetector(d, JavaParser.ExprInfixContext::class)
        v.registerDetector(d, JavaParser.ExprPostfixContext::class)
        v.registerDetector(d, JavaParser.ExprPrefixContext::class)

        val file = ParsedFile(CharStreams.fromString(code))

        return Triple(v, g, file)
    }

    private fun runVisitorExpr(code: String): List<List<Any>> {
        val (v, g, file) = parse(code)
        file.parser.expression().accept(v)
        return g.getAllAtomAppearances()
    }

    private fun runVisitorFile(code: String): List<List<Any>> {
        val (v, g, file) = parse(code)
        file.parser.compilationUnit().accept(v)
        return g.getAllAtomAppearances()
    }

    @Test
    fun testBasic() {
        val atoms = runVisitorExpr("a == 2 || a++")

        assertEquals(1, atoms.size)
        assertEquals("LOGIC_AS_CONTROL_FLOW", atoms[0][0])
    }

    @Test
    fun testPrefix() {
        val atoms = runVisitorExpr("a || ++a")

        assertEquals(1, atoms.size)
        assertEquals("LOGIC_AS_CONTROL_FLOW", atoms[0][0])
    }

    @Test
    fun testNested() {
        val atoms = runVisitorExpr("a || (a || a++)")

        assertEquals(1, atoms.size)
        assertEquals("LOGIC_AS_CONTROL_FLOW", atoms[0][0])
    }

    @Test
    fun testInClass() {
        val atoms = runVisitorFile("class A { void f() {if (a == 2 || a++) bar(); } }")

        assertEquals(1, atoms.size)
        assertEquals("LOGIC_AS_CONTROL_FLOW", atoms[0][0])
    }
}
