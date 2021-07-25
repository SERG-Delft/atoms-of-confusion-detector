package parsing.detectors

import org.antlr.v4.runtime.CharStreams
import output.graph.ConfusionGraph
import parsing.AtomsVisitor
import parsing.ParsedFile

open class DetectorTest {
    val visitor = AtomsVisitor()
    val graph = ConfusionGraph(listOf("f1"))

    lateinit var detector: Detector

    init {
        visitor.fileName = "f1"
    }

    private fun parse(code: String): Triple<AtomsVisitor, ConfusionGraph, ParsedFile> {

        // register detector
        visitor.registerDetector(detector)

        val file = ParsedFile(CharStreams.fromString(code))

        return Triple(visitor, graph, file)
    }

    protected fun runVisitorExpr(code: String): List<List<Any>> {
        val (v, g, file) = parse(code)
        file.parser.expression().accept(v)
        return g.getAllAtomAppearances()
    }

    protected fun runVisitorFile(code: String): List<List<Any>> {
        val (v, g, file) = parse(code)
        file.parser.compilationUnit().accept(v)
        return g.getAllAtomAppearances()
    }
}
