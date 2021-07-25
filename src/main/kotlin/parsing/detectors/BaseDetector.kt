package parsing.detectors

import JavaParser
import output.graph.ConfusionGraph
import parsing.AtomsVisitor

@SuppressWarnings("EmptyFunctionBlock")
open class BaseDetector(open val visitor: AtomsVisitor, open val graph: ConfusionGraph) : Detector {
    override fun detect(ctx: JavaParser.ExprInfixContext) {
        visitor.visitChildren(ctx)
    }

    override fun detect(ctx: JavaParser.ExprPrefixContext) {
        visitor.visitChildren(ctx)
    }

    override fun detect(ctx: JavaParser.ExprPostfixContext) {
        visitor.visitChildren(ctx)
    }

    override fun detect(ctx: JavaParser.ExprTernaryContext) {
        visitor.visitChildren(ctx)
    }
}
