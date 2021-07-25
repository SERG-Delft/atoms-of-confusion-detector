package parsing.detectors

import JavaParser
import org.antlr.v4.runtime.ParserRuleContext
import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsVisitor
import parsing.exceptions.NotInfixException

@Visit(
    JavaParser.ExprInfixContext::class,
    JavaParser.ExprInstanceofContext::class,
    JavaParser.ExprInfixBitshiftContext::class
)
class InfixPrecedenceDetector(visitor: AtomsVisitor, graph: ConfusionGraph) : Detector(visitor, graph) {

    @SuppressWarnings("MagicNumber")
    private val infixPrecedence = mutableMapOf(
        "*" to 1,
        "/" to 1,
        "%" to 1,
        "+" to 2,
        "-" to 2,
        "<<" to 3,
        ">>" to 3,
        ">>>" to 3,
        "<=" to 4,
        ">=" to 4,
        "<" to 4,
        ">" to 4,
        "instanceof" to 5,
        "==" to 6,
        "!=" to 6,
        "&" to 7,
        "^" to 8,
        "|" to 9,
        "&&" to 10,
        "||" to 11,
    )

    /**
     * Returns the precedence of the root operator of an infix expression
     *
     * @param e an expression
     * @return the precedence of the root operator of e
     */
    private fun infixPrecedence(e: ParserRuleContext): Int = when (e) {
        is JavaParser.ExprInfixContext -> infixPrecedence[e.op.text]!!
        is JavaParser.ExprInfixBitshiftContext -> infixPrecedence[">>"]!!
        is JavaParser.ExprInstanceofContext -> infixPrecedence["instanceof"]!!
        else -> throw NotInfixException(e.text)
    }

    /**
     * Returns true if the provided expression is infix
     *
     * @param e an expression
     * @return true if e is an infix expression
     */
    private fun isInfix(e: JavaParser.ExpressionContext): Boolean {
        return e is JavaParser.ExprInfixContext ||
            e is JavaParser.ExprInstanceofContext ||
            e is JavaParser.ExprInfixBitshiftContext
    }

    /**
     * Returns true if child is infix and higher precedence than parent. eg: parent: 1 + 1/2, child: 1/2 -> true
     *
     * @param parent the parent expression
     * @param child the child expression, intended to be either the left or right child
     * @return true if the child is an infix expression and is higher precedence than the parent
     */
    private fun checkPrecedence(parent: JavaParser.ExpressionContext, child: JavaParser.ExpressionContext): Boolean {
        return isInfix(child) && infixPrecedence(child) < infixPrecedence(parent)
    }

    override fun detect(ctx: JavaParser.ExprInfixContext) {
        val isConfusing = checkPrecedence(ctx, ctx.l) || checkPrecedence(ctx, ctx.r)
        if (isConfusing) graph.addAppearancesOfAtom(
            Atom.INFIX_OPERATOR_PRECEDENCE,
            visitor.fileName,
            mutableSetOf(ctx.start.line)
        )
        visitor.visitChildren(ctx)
    }

    override fun detect(ctx: JavaParser.ExprInfixBitshiftContext) {
        val isConfusing = checkPrecedence(ctx, ctx.l) || checkPrecedence(ctx, ctx.r)
        if (isConfusing) graph.addAppearancesOfAtom(
            Atom.INFIX_OPERATOR_PRECEDENCE,
            visitor.fileName,
            mutableSetOf(ctx.start.line)
        )
        visitor.visitChildren(ctx)
    }

    override fun detect(ctx: JavaParser.ExprInstanceofContext) {
        if (checkPrecedence(ctx, ctx.l)) graph.addAppearancesOfAtom(
            Atom.INFIX_OPERATOR_PRECEDENCE,
            visitor.fileName,
            mutableSetOf(ctx.start.line)
        )
        visitor.visitChildren(ctx)
    }
}
