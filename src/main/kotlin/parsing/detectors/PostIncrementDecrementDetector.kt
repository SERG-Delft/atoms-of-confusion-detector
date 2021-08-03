package parsing.detectors

import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

@Visit(JavaParser.ExprPostfixContext::class)
class PostIncrementDecrementDetector(listener: AtomsListener, graph: ConfusionGraph) : Detector(listener, graph) {

    override fun detect(ctx: JavaParser.ExprPostfixContext) {
        val parent = ctx.parent
        if ((
            parent !is JavaParser.StatExpressionContext &&
                parent !is JavaParser.ExpressionListContext &&
                parent != null
            ) || parent?.parent is JavaParser.MethodCallContext
        ) {
            graph.addAppearancesOfAtom(Atom.POST_INCREMENT_DECREMENT, listener.fileName, mutableSetOf(ctx.start.line))
        }
    }
}
