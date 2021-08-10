package parsing.detectors

import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

@Visit(JavaParser.ExprPrefixContext::class)
class PreIncrementDecrementDetector(listener: AtomsListener, graph: ConfusionGraph) : Detector(listener, graph) {

    override fun detect(ctx: JavaParser.ExprPrefixContext) {
        val parent = ctx.parent
        if (parent !is JavaParser.StatExpressionContext &&
            parent !is JavaParser.ExpressionListContext &&
            parent != null
        ) {
            graph.addAppearancesOfAtom(Atom.PRE_INCREMENT_DECREMENT, listener.fileName, mutableSetOf(ctx.start.line))
        }
    }
}
