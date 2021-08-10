package parsing.detectors

import output.graph.ConfusionGraph
import parsing.AtomsListener

@Suppress("TooManyFunctions")
open class Detector(open val listener: AtomsListener, open val graph: ConfusionGraph) {
    open fun detect(ctx: JavaParser.ExprInfixContext) {}
    open fun detect(ctx: JavaParser.ExprPrefixContext) {}
    open fun detect(ctx: JavaParser.ExprPostfixContext) {}
    open fun detect(ctx: JavaParser.ExprInstanceofContext) {}
    open fun detect(ctx: JavaParser.ExprInfixBitshiftContext) {}
    open fun detect(ctx: JavaParser.ExprTernaryContext) {}
    open fun detect(ctx: JavaParser.StatIfElseContext) {}
    open fun detect(ctx: JavaParser.StatForContext) {}
    open fun detect(ctx: JavaParser.StatWhileContext) {}
    open fun detect(ctx: JavaParser.StatDoWhileContext) {}
    open fun detect(ctx: JavaParser.StatBlockContext) {}
    open fun detect(ctx: JavaParser.StatExpressionContext) {}
    open fun detect(ctx: JavaParser.ExprTypeCastContext) {}
    open fun detect(ctx: JavaParser.ExprAssignmentContext) {}
}
