package parsing.detectors

import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

@Visit(JavaParser.ExprPrefixContext::class)
class PreIncrementDecrementDetector(listener: AtomsListener, graph: ConfusionGraph) : IncrementDecrementBaseDetector(
    listener,
    graph,
    Atom.PRE_INCREMENT_DECREMENT,
    Atom.PRE_INCREMENT_DECREMENT_IN_FOR_LOOP,
    Atom.PRE_INCREMENT_DECREMENT_AS_STATEMENT
) {

    override fun detect(ctx: JavaParser.ExprPrefixContext) {
        if (ctx.prefix.text == "++" || ctx.prefix.text == "--") super.analyzePostPreIncrementDecrement(
            ctx.parent,
            ctx.start.line
        )
    }
}
