package parsing.symtab.symbols

import org.antlr.symtab.Type
import org.antlr.symtab.VariableSymbol

open class AtomsBaseSymbol(open val myName: String, open val myType: Type, open var value: String?) :
    VariableSymbol(myName) {

    init {
        super.type = myType
    }
}
