package parsing.detectors

interface Detector {
    fun detect(ctx: JavaParser.ExprInfixContext)
    fun detect(ctx: JavaParser.ExprPrefixContext)
    fun detect(ctx: JavaParser.ExprPostfixContext)
    fun detect(ctx: JavaParser.ExprTernaryContext)
}
