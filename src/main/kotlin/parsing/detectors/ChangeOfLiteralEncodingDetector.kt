package parsing.detectors

import JavaParser
import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

@Visit(JavaParser.IntLitOctalContext::class)
class ChangeOfLiteralEncodingDetector(listener: AtomsListener, graph: ConfusionGraph) : Detector(listener, graph) {

    override fun detect(ctx: JavaParser.IntLitOctalContext) {
        graph.addAppearancesOfAtom(Atom.CHANGE_OF_LITERAL_ENCODING, listener.fileName, mutableSetOf(ctx.start.line))
    }
}
