package parsing.detectors

import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsVisitor

@Visit(JavaParser.ExprAssignmentContext::class)
class RepurposedVariablesDetector(visitor: AtomsVisitor, graph: ConfusionGraph) : Detector(visitor, graph) {

//    val variablesInForLoops = mutableSetOf()

    override fun detect(ctx: JavaParser.ExprAssignmentContext) {
        val assignee = ctx.assignee.text
        val assigned = ctx.assigned.text
        // check if variable is re-assigned to an independent value
        if (!assigned.contains(assignee)) {
            graph.addAppearancesOfAtom(
                    Atom.REPURPOSED_VARIABLES,
                    visitor.fileName,
                    mutableSetOf(ctx.start.line)
            )
        }
    }
}
