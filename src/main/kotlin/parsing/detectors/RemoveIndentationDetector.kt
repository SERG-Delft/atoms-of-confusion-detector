package parsing.detectors

import org.antlr.v4.runtime.ParserRuleContext
import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

@Visit(JavaParser.StatIfElseContext::class, JavaParser.StatWhileContext::class, JavaParser.StatForContext::class)
class RemoveIndentationDetector(listener: AtomsListener, graph: ConfusionGraph) : Detector(listener, graph) {

    override fun detect(ctx: JavaParser.StatIfElseContext) {
        val ifPart = ctx.IF()
        val elsePart = ctx.ELSE()
        if (ifPart != null && elsePart != null) {
            if (ifPart.symbol.charPositionInLine != elsePart.symbol.charPositionInLine) {
                graph.addAppearancesOfAtom(
                    Atom.REMOVE_INDENTATION,
                    listener.fileName,
                    mutableSetOf(ctx.elseBody.start.line)
                )
            }
        }
        if (ctx.ifBody != null &&
            ifPart != null &&
            ctx.ifBody.start.charPositionInLine == ifPart.symbol.charPositionInLine
        ) {
            graph.addAppearancesOfAtom(
                Atom.REMOVE_INDENTATION,
                listener.fileName,
                mutableSetOf(ctx.ifBody.start.line)
            )
        }
        if (ctx.elseBody != null &&
            elsePart != null &&
            ctx.elseBody.start.charPositionInLine == elsePart.symbol.charPositionInLine
        ) {
            graph.addAppearancesOfAtom(
                Atom.REMOVE_INDENTATION,
                listener.fileName,
                mutableSetOf(ctx.elseBody.start.line)
            )
        }
    }

    override fun detect(ctx: JavaParser.StatWhileContext) {
        analyzeInLoopStatement(ctx, ctx.statement())
    }

    override fun detect(ctx: JavaParser.StatForContext) {
        analyzeInLoopStatement(ctx, ctx.statement())
    }

    private fun analyzeInLoopStatement(parentLoop: ParserRuleContext, statement: JavaParser.StatementContext) {
        if (statement is JavaParser.BlockContext) {
            return
        }
        if (parentLoop.start.charPositionInLine == statement.start.charPositionInLine) {
            graph.addAppearancesOfAtom(
                Atom.REMOVE_INDENTATION,
                listener.fileName,
                mutableSetOf(statement.start.line)
            )
        }
        val parentOfTheLoop = parentLoop.parent ?: return
        val grandpa = parentOfTheLoop.parent ?: return
        val siblingsOfLoop = if (grandpa is ParserRuleContext) grandpa.children else return
        val indexOfLoop = siblingsOfLoop.indexOf(parentOfTheLoop)
        if (indexOfLoop + 1 < siblingsOfLoop.size) {
            val nextStatement = siblingsOfLoop[indexOfLoop + 1]
            if (nextStatement is ParserRuleContext &&
                nextStatement.start.charPositionInLine == statement.start.charPositionInLine
            ) {
                graph.addAppearancesOfAtom(
                    Atom.REMOVE_INDENTATION,
                    listener.fileName,
                    mutableSetOf(nextStatement.start.line)
                )
            }
        }
    }
}
