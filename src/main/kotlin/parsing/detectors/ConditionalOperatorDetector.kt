package parsing.detectors

import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

@Visit(JavaParser.ExprTernaryContext::class)
class ConditionalOperatorDetector(listener: AtomsListener, graph: ConfusionGraph) : Detector(listener, graph) {

    override fun detect(ctx: JavaParser.ExprTernaryContext) {
        graph.addAppearancesOfAtom(Atom.CONDITIONAL_OPERATOR, listener.fileName, mutableSetOf(ctx.start.line))
        super.detect(ctx)
    }
}
