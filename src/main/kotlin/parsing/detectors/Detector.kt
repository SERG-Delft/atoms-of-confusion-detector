package parsing.detectors

import output.graph.ConfusionGraph
import parsing.AtomsVisitor

open class Detector(open val visitor: AtomsVisitor, open val graph: ConfusionGraph) {
    open fun detect(ctx: JavaParser.ExprInfixContext) {}
    open fun detect(ctx: JavaParser.ExprPrefixContext) {}
    open fun detect(ctx: JavaParser.ExprPostfixContext) {}
    open fun detect(ctx: JavaParser.ExprInstanceofContext) {}
    open fun detect(ctx: JavaParser.ExprInfixBitshiftContext) {}
    open fun detect(ctx: JavaParser.ExprTernaryContext) {}
}
