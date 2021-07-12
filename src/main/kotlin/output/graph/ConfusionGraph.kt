package output.graph

import output.graph.nodes.AtomNode
import output.graph.nodes.SourceNode


class ConfusionGraph(val sources : List<String>) {

    val atomNodes = hashMapOf<String, AtomNode>(
        "InfixOperatorPrecedence" to AtomNode("InfixOperatorPrecedence"),
        "PostIncrementDecrement" to AtomNode("PostIncrementDecrement"),
        "ConstantVariables" to AtomNode("ConstantVariables"),
        "RemoveIndentationAtom" to AtomNode("RemoveIndentationAtom"),
        "ConditionalOperator" to AtomNode("ConditionalOperator"),
        "ArithmeticAsLogic" to AtomNode("ArithmeticAsLogic"),
        "LogicAsControlFlow" to AtomNode("LogicAsControlFlow"),
        "RepurposedVariables" to AtomNode("RepurposedVariables"),
        "DeadUnreachableRepeated" to AtomNode("DeadUnreachableRepeated"),
        "ChangeOfLiteralEncoding" to AtomNode("ChangeOfLiteralEncoding"),
        "OmittedCurlyBraces" to AtomNode("OmittedCurlyBraces"),
        "TypeConversion" to AtomNode("TypeConversion"),
        "Indentation" to AtomNode("Indentation")
    )

    val sourceNodes = mutableMapOf<String, SourceNode>()

    init {
        sources.forEach {
            sourceNodes[it] = SourceNode(it)
        }
    }

    fun union(otherGraph : ConfusionGraph) {
        TODO()
    }

}