package parsing.detectors

import JavaParser
import org.antlr.v4.runtime.tree.ParseTree
import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener
import parsing.ParseTreeUtil

@Visit(JavaParser.StatForContext::class, JavaParser.StatWhileContext::class, JavaParser.StatIfElseContext::class)
class OmittedCurlyBracesDetector(listener: AtomsListener, graph: ConfusionGraph) : Detector(listener, graph) {

    /**
     * Check the tokens in between two nodes. If a newline is contained return true
     *
     * @param start the initial token
     * @param stop the second token
     * @return true if there is a whitespace in between the end of start and beginning of stop
     */
    fun checkWs(start: ParseTree, stop: ParseTree): Boolean {

        val wsStart = start.sourceInterval.b + 1
        val wsStop = stop.sourceInterval.a - 1

        // for each whitespace token separating the statements check if there is a newline
        for (i in wsStart..wsStop) {
            if (listener.file.tokens[i].text.contains("\n")) return true
        }

        return false
    }

    override fun detect(ctx: JavaParser.StatForContext) {

        // if the body is not a block, and the loop is a block statement
        if (ctx.body !is JavaParser.StatBlockContext && ParseTreeUtil.isBlockStatement(ctx)) {

            val nextBlockStatement = ParseTreeUtil.nextBlockStatement(ctx) ?: return

            // check fi there is no ws in between statements, if so detect the atom
            if (!checkWs(ctx, nextBlockStatement)) {
                graph.addAppearancesOfAtom(Atom.OMITTED_CURLY_BRACES, listener.fileName, mutableSetOf(ctx.start.line))
            }
        }
    }

    override fun detect(ctx: JavaParser.StatWhileContext) {

        // if the body is not a block, and the loop is a block statement
        if (ctx.body !is JavaParser.StatBlockContext && ParseTreeUtil.isBlockStatement(ctx)) {

            val nextBlockStatement = ParseTreeUtil.nextBlockStatement(ctx) ?: return

            // check fi there is no ws in between statements, if so detect the atom
            if (!checkWs(ctx, nextBlockStatement)) {
                graph.addAppearancesOfAtom(Atom.OMITTED_CURLY_BRACES, listener.fileName, mutableSetOf(ctx.start.line))
            }
        }
    }
}
