package parsing.detectors

import org.antlr.v4.runtime.ParserRuleContext
import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

@Visit(JavaParser.StatIfElseContext::class, JavaParser.StatWhileContext::class, JavaParser.StatForContext::class)
class IndentationDetector(listener: AtomsListener, graph: ConfusionGraph) : Detector(listener, graph) {

    override fun detect(ctx: JavaParser.StatIfElseContext) {
        checkNextSiblingHasSameIndentation(ctx)
        val startingIndentOfIf = ctx.IF().symbol.charPositionInLine
        if (ctx.ifBody is JavaParser.StatBlockContext) {
            val closingBracket = (ctx.ifBody as JavaParser.StatBlockContext).block().RBRACE().symbol
            val indentOfClosingBracket =
                closingBracket.charPositionInLine
            if (indentOfClosingBracket != startingIndentOfIf) {
                graph.addAppearancesOfAtom(
                    Atom.INDENTATION,
                    listener.fileName,
                    mutableSetOf(closingBracket.line)
                )
            }
        }
    }

    override fun detect(ctx: JavaParser.StatWhileContext) {
        checkNextSiblingHasSameIndentation(ctx)
    }

    override fun detect(ctx: JavaParser.StatForContext) {
        checkNextSiblingHasSameIndentation(ctx)
    }

    private fun checkNextSiblingHasSameIndentation(ctx: ParserRuleContext) {
        val parent = ctx.parent ?: return
        val granpa = parent.parent ?: return
        val siblings = if (granpa is ParserRuleContext) granpa.children else return
        val indexOfElement = siblings.indexOf(parent)
        if (indexOfElement + 1 < siblings.size) {
            val nextStatement = siblings[indexOfElement + 1]
            if (nextStatement is ParserRuleContext &&
                nextStatement.start.charPositionInLine != ctx.start.charPositionInLine
            ) {
                graph.addAppearancesOfAtom(
                    Atom.INDENTATION,
                    listener.fileName,
                    mutableSetOf(nextStatement.start.line)
                )
            }
        }
    }
}
