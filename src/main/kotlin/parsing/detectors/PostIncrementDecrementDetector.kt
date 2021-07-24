package parsing.detectors

import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsVisitor

@Visit(JavaParser.ExprPostfixContext::class)
class PostIncrementDecrementDetector(visitor: AtomsVisitor, graph: ConfusionGraph) : BaseDetector(visitor, graph) {

    override fun detect(ctx: JavaParser.ExprPostfixContext) {
        val parent = ctx.parent
        if (parent !is JavaParser.StatExpressionContext &&
            parent !is JavaParser.ExpressionListContext &&
            parent != null
        ) {
            graph.addAppearancesOfAtom(Atom.POST_INCREMENT_DECREMENT, visitor.fileName, mutableSetOf(ctx.start.line))
        }
    }
}
