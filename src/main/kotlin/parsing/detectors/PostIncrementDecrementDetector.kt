package parsing.detectors

import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

@Visit(JavaParser.ExprPostfixContext::class)
class PostIncrementDecrementDetector(listener: AtomsListener, graph: ConfusionGraph) :
    IncrementDecrementBaseDetector(
        listener,
        graph,
        Atom.POST_INCREMENT_DECREMENT,
        Atom.POST_INCREMENT_DECREMENT_IN_FOR_LOOP,
        Atom.POST_INCREMENT_DECREMENT_AS_STATEMENT
    ) {

    override fun detect(ctx: JavaParser.ExprPostfixContext) {
        super.analyzePostPreIncrementDecrement(ctx.parent, ctx.start.line)
    }
}
