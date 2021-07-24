package parsing.detectors

import output.graph.ConfusionGraph
import parsing.AtomsVisitor

// this is not just about assignments but also about any arithmetic
@Visit(JavaParser.ExprAssignmentContext::class)
class ConstantVariableDetector(visitor: AtomsVisitor, graph: ConfusionGraph) : BaseDetector(visitor, graph) {

    override fun detect(ctx: JavaParser.ExprAssignmentContext) {
        // to correctly detect this atom we need to be able to resolve identifiers
        val line = ctx.start.line
    }
}
