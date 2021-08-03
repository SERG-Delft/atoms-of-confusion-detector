package parsing.detectors

import output.Atom
import output.graph.ConfusionGraph
import parsing.AtomsListener

@Visit(JavaParser.ExprTypeCastContext::class)
class TypeConversionDetector(listener: AtomsListener, graph: ConfusionGraph) : Detector(listener, graph) {

    private val dangerousCasts = hashSetOf("byte", "int", "short", "long", "Byte", "Integer", "Short", "Long")

    override fun detect(ctx: JavaParser.ExprTypeCastContext) {

        val castedType = ctx.cast.text

        if (dangerousCasts.contains(castedType)) {
            graph.addAppearancesOfAtom(Atom.TYPE_CONVERSION, listener.fileName, mutableSetOf(ctx.start.line))
        }
    }
}
