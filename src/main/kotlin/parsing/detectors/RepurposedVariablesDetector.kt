package parsing.detectors

import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

@Visit(JavaParser.ExprAssignmentContext::class, JavaParser.ForControlContext::class)
class RepurposedVariablesDetector(listener: AtomsListener, graph: ConfusionGraph) : Detector(listener, graph) {

    /**
     * Detects the atom if an identifier is assigned to a value
     * independent of itself.
     *
     * @param ctx the assignment node in the parse tree.
     */
    override fun detect(ctx: JavaParser.ExprAssignmentContext) {
        val assignee = ctx.assignee.text
        val assigned = ctx.assigned.text
        // check if the assignment is in a for loop iter update
        val grandpa = ctx.parent.parent
        if (grandpa is JavaParser.ForControlContext) {
            analyzeSubExpr(ctx.assignee)
        } else if (!assigned.contains(assignee)) {
            // check if variable is re-assigned to an independent value
            graph.addAppearancesOfAtom(
                Atom.REPURPOSED_VARIABLES,
                listener.fileName,
                mutableSetOf(ctx.start.line)
            )
        }
    }

    /**
     * Detects the atom if in the declaration of the for loop the iteration updater (e.g i++)
     * uses a variable not defined in the scope of this for loop.
     *
     * @param ctx the for-loop control node in the parse tree.
     */
    override fun detect(ctx: JavaParser.ForControlContext) {
        val iterUpdate = ctx.iterUpdate
        iterUpdate.children.forEach {
            when (it) {
                is JavaParser.ExprPostfixContext -> {
                    analyzeSubExpr(it.subexpr)
                }
                is JavaParser.ExprPrefixContext -> {
                    analyzeSubExpr(it.subexpr)
                }
                is JavaParser.ExprInfixContext -> {
                    analyzeSubExpr(it.l)
                }
            }
        }
    }

    private fun analyzeSubExpr(ctx: JavaParser.ExpressionContext) {
        if (ctx is JavaParser.ExprIdentifierContext) {
            val textIdentifier = ctx.IDENTIFIER().text.toString()
            val sym = this.listener.currentScope?.resolve(textIdentifier)
            // since the scope is updated after this point if the identifier
            // is not null it means that it was defined in an outer scope
            if (sym != null) {
                graph.addAppearancesOfAtom(
                    Atom.REPURPOSED_VARIABLES,
                    listener.fileName,
                    mutableSetOf(ctx.start.line)
                )
            }
        }
    }
}
