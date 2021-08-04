package parsing.detectors

import org.antlr.v4.runtime.RuleContext
import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

open class IncrementDecrementBaseDetector(
    override val listener: AtomsListener,
    override val graph: ConfusionGraph,
    private val atomInContext: Atom,
    private val atomInForLoop: Atom,
    private val atomAsStatement: Atom
) :
    Detector(listener, graph) {

    fun analyzePostPreIncrementDecrement(parent: RuleContext?, lineNum: Int) {
        val line = mutableSetOf(lineNum)
        if (parent is JavaParser.StatExpressionContext || parent == null) {
            graph.addAppearancesOfAtom(atomAsStatement, listener.fileName, line)
        } else if (parent?.parent is JavaParser.ForControlContext) {
            graph.addAppearancesOfAtom(atomInForLoop, listener.fileName, line)
        } else {
            graph.addAppearancesOfAtom(atomInContext, listener.fileName, line)
        }
    }
}
