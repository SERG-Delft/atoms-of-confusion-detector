package parsing.detectors

import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsVisitor

@Visit(JavaParser.ExprTernaryContext::class)
class ConditionalOperatorDetector(visitor: AtomsVisitor, graph: ConfusionGraph) : BaseDetector(visitor, graph) {

    override fun detect(ctx: JavaParser.ExprTernaryContext) {
        graph.addAppearancesOfAtom(Atom.CONDITIONAL_OPERATOR, visitor.fileName, mutableSetOf(ctx.start.line))
        super.detect(ctx)
    }
}
