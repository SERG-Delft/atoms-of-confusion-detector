package parsing

import JavaParserBaseVisitor
import org.antlr.v4.runtime.ParserRuleContext
import output.Atom
import output.graph.ConfusionGraph

class AtomsVisitor(val graph: ConfusionGraph, private val fileName: String) : JavaParserBaseVisitor<Unit>() {

    // smaller number -> evaluated earlier
    @SuppressWarnings("MagicNumber")
    private val infixPrecedence = mutableMapOf<String, Int>(
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

    private var insideShortCircuitRight = false

    override fun visitExprTernary(ctx: JavaParser.ExprTernaryContext) {
        val line = ctx.start.line
        graph.addAppearancesOfAtom(Atom.CONDITIONAL_OPERATOR, fileName, mutableSetOf(line))
        super.visitExprTernary(ctx)
    }

    override fun visitExprInfix(ctx: JavaParser.ExprInfixContext) {
        val isConfusing = (isInfix(ctx.l) && infixPrecedence(ctx.l) < infixPrecedence(ctx)) ||
            (isInfix(ctx.r) && infixPrecedence(ctx.r) < infixPrecedence(ctx))
        if (isConfusing) {
            graph.addAppearancesOfAtom(Atom.INFIX_OPERATOR_PRECEDENCE, fileName, mutableSetOf(ctx.start.line))
        }
        visit(ctx.l)
        insideShortCircuitRight = ctx.op.text == "||" || ctx.op.text == "&&"
        visit(ctx.r)
        insideShortCircuitRight = false
    }

    override fun visitExprPostfix(ctx: JavaParser.ExprPostfixContext) {
        val line = ctx.start.line
        if (insideShortCircuitRight) graph.addAppearancesOfAtom(
            Atom.LOGIC_AS_CONTROL_FLOW,
            fileName,
            mutableSetOf(line)
        )
        super.visitExprPostfix(ctx)
    }

    override fun visitExprPrefix(ctx: JavaParser.ExprPrefixContext) {
        val line = ctx.start.line
        if (insideShortCircuitRight && (ctx.prefix.text == "++" || ctx.prefix.text == "--")) graph.addAppearancesOfAtom(
            Atom.LOGIC_AS_CONTROL_FLOW,
            fileName,
            mutableSetOf(line)
        )
        super.visitExprPrefix(ctx)
    }

    @SuppressWarnings("TooGenericExceptionThrown")
    fun infixPrecedence(expr: ParserRuleContext): Int = when (expr) {
        is JavaParser.ExprInfixContext -> infixPrecedence[expr.op.text]!!
        is JavaParser.ExprInfixBitshiftContext -> infixPrecedence[">>"]!!
        is JavaParser.ExprInstanceofContext -> infixPrecedence["instanceof"]!!
        else -> throw Exception("expr is not an infix expression")
    }

    fun isInfix(e: JavaParser.ExpressionContext) = e is JavaParser.ExprInfixContext ||
        e is JavaParser.ExprInstanceofContext ||
        e is JavaParser.ExprInfixBitshiftContext
}
