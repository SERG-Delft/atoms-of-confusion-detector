package parsing.detectors

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.tree.ParseTreeWalker
import output.graph.ConfusionGraph
import parsing.AtomsListener
import parsing.ParsedFile
import kotlin.test.assertEquals

open class DetectorTest {
    val listener = AtomsListener()
    val graph = ConfusionGraph(listOf("f1"))

    lateinit var detector: Detector

    init {
        listener.fileName = "f1"
    }

    private fun parse(code: String): Triple<AtomsListener, ConfusionGraph, ParsedFile> {

        // register detector
        listener.registerDetector(detector)

        val file = ParsedFile(CharStreams.fromString(code))

        return Triple(listener, graph, file)
    }

    protected fun runVisitorExpr(code: String): List<List<Any>> {
        val (atomListener, graph, file) = parse(code)
        val tree = file.parser.expression()
        val walker = ParseTreeWalker()
        walker.walk(atomListener, tree)
        return graph.getAllAtomAppearances()
    }

    protected fun runVisitorFile(code: String): List<List<Any>> {
        val (atomListener, graph, file) = parse(code)
        val tree = file.parser.compilationUnit()
        val walker = ParseTreeWalker()
        walker.walk(atomListener, tree)
        return graph.getAllAtomAppearances()
    }

    protected fun assertAtom(atoms: List<List<Any>>, expectedAtom: String) {
        assertEquals(1, atoms.size)
        assertEquals(expectedAtom, atoms[0][0])
    }
}
