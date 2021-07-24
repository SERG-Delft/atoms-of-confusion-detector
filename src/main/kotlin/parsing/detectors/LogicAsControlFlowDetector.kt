package parsing.detectors

import JavaParser
import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsVisitor

@Visit(JavaParser.ExprPrefixContext::class, JavaParser.ExprInfixContext::class, JavaParser.ExprPostfixContext::class)
class LogicAsControlFlowDetector(visitor: AtomsVisitor, graph: ConfusionGraph) : BaseDetector(visitor, graph) {

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
        super.detect(ctx)
    }

    override fun detect(ctx: JavaParser.ExprPrefixContext) {
        val line = ctx.start.line
        if (insideShortCircuitRight && (ctx.prefix.text == "++" || ctx.prefix.text == "--")) graph.addAppearancesOfAtom(
            Atom.LOGIC_AS_CONTROL_FLOW,
            visitor.fileName,
            mutableSetOf(line)
        )
        super.detect(ctx)
    }
}
