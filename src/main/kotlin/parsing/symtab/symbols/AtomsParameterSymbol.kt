package parsing.symtab.symbols

import org.antlr.symtab.Type

class AtomsParameterSymbol(override val myName: String, override val myType: Type) :
    AtomsBaseSymbol(myName, myType, null) {

    init {
        super.type = myType
    }

    override fun toString(): String {
        return "AtomsParameterSymbol(name=$myName, type=$myType)"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is AtomsParameterSymbol) return false
        return other.myName == this.myName && other.myType.name == this.myType.name
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + myName.hashCode()
        result = 31 * result + myType.hashCode()
        return result
    }
}
