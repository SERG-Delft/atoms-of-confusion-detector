package parsing.symtab.symbols

import org.antlr.symtab.MemberSymbol
import org.antlr.symtab.SymbolWithScope

class AtomsConstructorSymbol(
    val myName: String,
    val parameters: MutableSet<AtomsParameterSymbol>
) :
    SymbolWithScope(myName), MemberSymbol {

    override fun toString(): String {
        return "AtomsConstructorSymbol(name=$myName)"
    }

    override fun getSlotNumber(): Int {
        return -1
    }
}
