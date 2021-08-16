package parsing.detectors

import JavaParser
import output.graph.ConfusionGraph
import parsing.AtomsListener

open class Detector(open val listener: AtomsListener, open val graph: ConfusionGraph) {
    open fun detect(ctx: JavaParser.ExprInfixContext) {}
    open fun detect(ctx: JavaParser.ExprPrefixContext) {}
    open fun detect(ctx: JavaParser.ExprPostfixContext) {}
    open fun detect(ctx: JavaParser.ExprInstanceofContext) {}
    open fun detect(ctx: JavaParser.ExprInfixBitshiftContext) {}
    open fun detect(ctx: JavaParser.ExprTernaryContext) {}
    open fun detect(ctx: JavaParser.IntLitOctalContext) {}
    open fun detect(ctx: JavaParser.ExprTypeCastContext) {}
}
