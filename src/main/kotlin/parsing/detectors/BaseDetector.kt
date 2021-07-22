package parsing.detectors

import output.graph.ConfusionGraph
import parsing.AtomsVisitor

@SuppressWarnings("EmptyFunctionBlock")
abstract class BaseDetector(open val visitor: AtomsVisitor, open val graph: ConfusionGraph) : Detector {
    override fun detect(ctx: JavaParser.ExprInfixContext) {}
    override fun detect(ctx: JavaParser.ExprPrefixContext) {}
    override fun detect(ctx: JavaParser.ExprPostfixContext) {}
}
