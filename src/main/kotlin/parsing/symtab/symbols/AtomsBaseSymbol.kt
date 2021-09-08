package parsing.symtab.symbols

import org.antlr.symtab.Type
import org.antlr.symtab.VariableSymbol
import org.antlr.v4.runtime.ParserRuleContext

open class AtomsBaseSymbol(
    open val myName: String,
    open val myType: Type,
    open var value: String?
) :
    VariableSymbol(myName) {

    // use this value to store the value of the symbol
    // as a parse tree node
    var parseTreeNodeValue: ParserRuleContext? = null

    init {
        super.type = myType
    }
}
