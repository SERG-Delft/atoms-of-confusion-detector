package parsing.detectors

import JavaParser
import org.antlr.v4.runtime.tree.ParseTreeWalker
import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

@Visit(JavaParser.ExprPrefixContext::class, JavaParser.ExprInfixContext::class, JavaParser.ExprPostfixContext::class)
class LogicAsControlFlowDetector(listener: AtomsListener, graph: ConfusionGraph) : Detector(listener, graph) {

    private var insideShortCircuitRight = false

    override fun detect(ctx: JavaParser.ExprInfixContext) {
//        this is a slightly dirty fix - there might be a more efficient way
//        we should improve on this in a future issue
        val walker = ParseTreeWalker()
        walker.walk(listener, ctx.l)
        insideShortCircuitRight = ctx.op.text == "||" || ctx.op.text == "&&"
        walker.walk(listener, ctx.r)
        insideShortCircuitRight = false
//        listener.visit(ctx.l)
//        insideShortCircuitRight = ctx.op.text == "||" || ctx.op.text == "&&"
//        listener.visit(ctx.r)
//        insideShortCircuitRight = false
    }

    override fun detect(ctx: JavaParser.ExprPostfixContext) {
        val line = ctx.start.line
        if (insideShortCircuitRight) graph.addAppearancesOfAtom(
            Atom.LOGIC_AS_CONTROL_FLOW,
            listener.fileName,
            mutableSetOf(line)
        )
    }

    override fun detect(ctx: JavaParser.ExprPrefixContext) {
        val line = ctx.start.line
        if (insideShortCircuitRight && (ctx.prefix.text == "++" || ctx.prefix.text == "--")) graph.addAppearancesOfAtom(
            Atom.LOGIC_AS_CONTROL_FLOW,
            listener.fileName,
            mutableSetOf(line)
        )
    }
}
