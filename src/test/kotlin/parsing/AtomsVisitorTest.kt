package parsing

import JavaParser
import org.antlr.v4.runtime.CharStreams
import org.junit.jupiter.api.Test
import output.graph.ConfusionGraph
import parsing.detectors.BaseDetector
import kotlin.test.assertEquals

internal class AtomsVisitorTest {

    @Test
    fun test() {

        val atoms = mutableListOf<String>()

        val g = ConfusionGraph(listOf("f1"))
        val v = AtomsVisitor()
        v.fileName = "f1"

        val detector = object : BaseDetector(v, g) {

            private var insideSCR = false

            override fun detect(ctx: JavaParser.ExprInfixContext) {
                visitor.visit(ctx.l)
                insideSCR = ctx.op.text == "||"
                visitor.visit(ctx.r)
                insideSCR = false
            }

            override fun detect(ctx: JavaParser.ExprPostfixContext) {
                if (insideSCR) atoms.add(ctx.text)
            }
        }

        v.registerDetector(detector, JavaParser.ExprInfixContext::class)
        v.registerDetector(detector, JavaParser.ExprPostfixContext::class)

        val file = ParsedFile(CharStreams.fromString("a == 2 || a++"))
        file.parser.expression().accept(v)

        assertEquals(listOf("a++"), atoms)
    }
}
