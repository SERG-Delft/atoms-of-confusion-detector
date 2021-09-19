package parsing.symtab.types

import org.antlr.symtab.ArrayType
import org.antlr.symtab.PrimitiveType
import org.antlr.symtab.Type

object TypeResolver {

    private val primitives = setOf(
        "byte", "short", "int", "long",
        "float", "double", "char", "boolean"
    )

    /**
     * Convert a textual type to a Type object that can be used for scoping.
     *
     * @param type the textual type to convert.
     * @returns a Type object.
     */
    fun resolveType(type: String): Type {
        return if (primitives.contains(type)) {
            PrimitiveType(type)
        } else if (type.endsWith("[]") && primitives.contains(type.substring(0, type.length - 2))) {
            ArrayType(PrimitiveType(type.substring(0, type.length - 2)))
        } else if (type.endsWith("[]") && !primitives.contains(type.substring(0, type.length - 2))) {
            ArrayType(ReferenceType(type))
        } else if (type == "void") {
            VoidType()
        } else {
            ReferenceType(type)
        }
    }
}
