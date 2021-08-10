package parsing.symtab.types

import org.antlr.symtab.Type

class ReferenceType(val myName: String) : Type {

    override fun getName(): String {
        return myName
    }

    override fun getTypeIndex(): Int {
        return -1
    }
}
