package parsing.detectors

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
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
        val identifiers = getIdentifiersInExpression(ctx)
        var containsNonLiteralIdentifier =
            false // use this to keep track of whether one of the identifiers does not resolve to a literal
        var constantVariablesCounter = 0 // counts the #appearances of the atom
        identifiers?.forEach {
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

    private fun getIdentifiersInExpression(ctx: JavaParser.ExprInfixContext): MutableCollection<ParseTree>? {
        return XPath.findAll(ctx, "//IDENTIFIER", listener.parsedFile.parser)
    }

    private fun isLiteral(ctx: ParserRuleContext?): Boolean {
        return if (ctx is JavaParser.VariableInitializerContext) {
            ctx.children[0] is JavaParser.ExprLiteralContext
        } else {
            ctx is JavaParser.ExprLiteralContext
        }
    }
}
