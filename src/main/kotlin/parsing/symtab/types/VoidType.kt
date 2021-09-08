package parsing.symtab.types

import org.antlr.symtab.Type

class VoidType : Type {
    override fun getName(): String {
        return "void"
    }

    override fun getTypeIndex(): Int {
        return -1
    }
}
