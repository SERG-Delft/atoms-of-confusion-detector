package parsing.detectors

import JavaParser
import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsVisitor

class LogicAsControlFlowDetector(override val visitor: AtomsVisitor, override val graph: ConfusionGraph) :
    BaseDetector(visitor, graph) {

    private var insideShortCircuitRight = false

    override fun detect(ctx: JavaParser.ExprInfixContext) {
        visitor.visit(ctx.l)
        insideShortCircuitRight = ctx.op.text == "||" || ctx.op.text == "&&"
        visitor.visit(ctx.r)
        insideShortCircuitRight = false
    }

    override fun detect(ctx: JavaParser.ExprPostfixContext) {
        val line = ctx.start.line
        if (insideShortCircuitRight) graph.addAppearancesOfAtom(
            Atom.LOGIC_AS_CONTROL_FLOW,
            visitor.fileName,
            mutableSetOf(line)
        )
        visitor.visitChildren(ctx)
    }

    override fun detect(ctx: JavaParser.ExprPrefixContext) {
        val line = ctx.start.line
        if (insideShortCircuitRight && (ctx.prefix.text == "++" || ctx.prefix.text == "--")) graph.addAppearancesOfAtom(
            Atom.LOGIC_AS_CONTROL_FLOW,
            visitor.fileName,
            mutableSetOf(line)
        )
        visitor.visitChildren(ctx)
    }
}
