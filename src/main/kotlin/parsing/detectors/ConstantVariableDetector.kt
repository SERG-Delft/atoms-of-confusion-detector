package parsing.detectors

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.xpath.XPath
import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener
import parsing.symtab.symbols.AtomsBaseSymbol

@Visit(
    JavaParser.VariableDeclaratorContext::class,
    JavaParser.ExprInfixContext::class,
    JavaParser.ExprPostfixContext::class,
    JavaParser.ExprPrefixContext::class
)
class ConstantVariableDetector(listener: AtomsListener, graph: ConfusionGraph) : Detector(listener, graph) {

    override fun detect(ctx: JavaParser.VariableDeclaratorContext) {

        // we only care about variable initializers, not array initializers
        if (ctx.variableInitializer() == null) return

        val initializerExpression = ctx.variableInitializer().expression()
        val line = ctx.start.line
        if (initializerExpression is JavaParser.ExprIdentifierContext) {
            val identifier = initializerExpression.IDENTIFIER().text
            if (listener.currentScope?.resolve(identifier) != null) {
                graph.addAppearancesOfAtom(
                    Atom.CONSTANT_VARIABLES,
                    listener.fileName,
                    mutableSetOf(line)
                )
            }
        }
    }

    override fun detect(ctx: JavaParser.ExprPostfixContext) {
        if ((ctx.DEC() != null || ctx.INC() != null) && ctx.parent is JavaParser.VariableInitializerContext) {
            val line = ctx.start.line
            graph.addAppearancesOfAtom(
                Atom.CONSTANT_VARIABLES,
                listener.fileName,
                mutableSetOf(line)
            )
        }
    }

    override fun detect(ctx: JavaParser.ExprPrefixContext) {
        if ((ctx.DEC() != null || ctx.INC() != null) && ctx.parent is JavaParser.VariableInitializerContext) {
            val line = ctx.start.line
            graph.addAppearancesOfAtom(
                Atom.CONSTANT_VARIABLES,
                listener.fileName,
                mutableSetOf(line)
            )
        }
    }

    /**
     * Analyze infix expressions for the Constant Variables atom.
     * The atom is detected iff the infix expression is either passed in a function or used in an assignment.
     * For example if  we have `int v1 = 4;` and then we have one of the following :
     * `System.out.println(v1*3.2)` or `int v2 = v1 + 434`.
     * For each identifier that resolves in the literal the infix expression.
     */
    override fun detect(ctx: JavaParser.ExprInfixContext) {
        if (ctx.parent !is JavaParser.VariableInitializerContext && ctx.parent !is JavaParser.ExpressionListContext) {
            // if the parent is anything else than the above then we don't have to do the analysis
            // this also ensures that the rest of the code run only for top level infix expressions
            return
        }
        val identifiers = getVariablesInExpression(ctx)
        var containsNonLiteralIdentifier =
            false // use this to keep track of whether one of the identifiers does not resolve to a literal
        var constantVariablesCounter = 0 // counts the #appearances of the atom
        identifiers.forEach {
            val resolvedSymbol = listener.currentScope?.resolve(it.text)
            resolvedSymbol as AtomsBaseSymbol
            if (isLiteral(resolvedSymbol.parseTreeNodeValue)) {
                constantVariablesCounter++
            } else {
                containsNonLiteralIdentifier = true
            }
        }
        val line = ctx.start.line
        if (!containsNonLiteralIdentifier) {
            while (constantVariablesCounter > 0) {
                graph.addAppearancesOfAtom(
                    Atom.CONSTANT_VARIABLES,
                    listener.fileName,
                    mutableSetOf(line)
                )
                constantVariablesCounter--
            }
        }
    }

    /**
     * Extract all of the variable literals in the expression
     *
     * @param ctx an infix expression
     * @return a list of ExprIdentifiers corresponding to variable literals in ctx
     */
    fun getVariablesInExpression(ctx: JavaParser.ExprInfixContext): List<JavaParser.ExprIdentifierContext> {
        return XPath.findAll(ctx, "//expression", listener.parsedFile.parser)
            .filterIsInstance<JavaParser.ExprIdentifierContext>()
            .filter { it.parent !is JavaParser.ExprDotAccessContext }
    }

    private fun isLiteral(ctx: ParserRuleContext?): Boolean {
        return if (ctx is JavaParser.VariableInitializerContext) {
            ctx.children[0] is JavaParser.ExprLiteralContext
        } else {
            ctx is JavaParser.ExprLiteralContext
        }
    }
}
