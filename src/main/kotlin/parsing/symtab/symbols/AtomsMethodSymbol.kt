package parsing.symtab.symbols

import org.antlr.symtab.MethodSymbol
import org.antlr.symtab.Type

class AtomsMethodSymbol(val myName: String, val returnType: Type) : MethodSymbol(myName) {

    init {
        super.setType(returnType)
    }

    override fun toString(): String {
        return "AtomsMethodSymbol(name=$myName, type=$returnType)"
    }
}
