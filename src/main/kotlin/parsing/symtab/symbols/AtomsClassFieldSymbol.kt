package parsing.symtab.symbols

import org.antlr.symtab.MemberSymbol
import org.antlr.symtab.Type

data class AtomsClassFieldSymbol(override val myName: String, override val myType: Type, override var value: String?) :
    AtomsBaseSymbol(myName, myType, value), MemberSymbol {

    init {
        super.type = myType
    }

    override fun toString(): String {
        return "ClassFieldSymbol(name=$myName, type=$myType, value=$value)"
    }

    override fun getSlotNumber(): Int {
        return -1
    }

    override fun equals(other: Any?): Boolean {
        if (other !is AtomsClassFieldSymbol) return false
        return other.myName == this.myName && other.myType.name == this.myType.name
    }
}
