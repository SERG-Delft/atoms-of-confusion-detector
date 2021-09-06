package parsing.symtab

import java.lang.StringBuilder

sealed class SymtabUtil {

    companion object {

        /**
         * Get the symbol identifier for a method in the following format:
         * retType.identifier(comma separated param types), eg: void.foo(int,String)
         *
         * @param declaration a method declaration ctx
         * @return the symbol id for the method
         */
        fun getMethodSymbolId(declaration: JavaParser.MethodDeclarationContext): String {

            val methodId = StringBuilder()
            methodId.append(declaration.typeTypeOrVoid().text)
            methodId.append(".")
            methodId.append(declaration.IDENTIFIER().text)

            // no parameters
            if (declaration.formalParameters().formalParameterList() == null) {
                methodId.append("()")
                return methodId.toString()
            }

            val paramTypes = declaration.formalParameters().formalParameterList().children
                .filter { it.text != "," }
                .map {

                    when (it) {
                        is JavaParser.FormalParameterContext -> {
                            it.typeType().text
                        }
                        is JavaParser.LastFormalParameterContext -> {
                            it.typeType().text
                        }
                        else -> {

                            // this can never be reached due to the grammar
                            ""
                        }
                    }
                }

            // single parameter
            if (paramTypes.size == 1) {
                methodId.append("(${paramTypes[0]})")
                return methodId.toString()
            }

            // multiple parameters, write params in comma separated format
            methodId.append("(")
            for (i in 0 until paramTypes.size - 1) methodId.append("${paramTypes[i]},")
            methodId.append("${paramTypes[paramTypes.lastIndex]})")

            return methodId.toString()
        }

        /**
         * Get the symbol identifier for an interface method in the following format:
         * retType.identifier(comma separated param types), eg: void.foo(int,String)
         *
         * @param declaration a method declaration ctx
         * @return the symbol id for the method
         */
        fun getInterfaceMethodSymbolId(declaration: JavaParser.InterfaceMethodDeclarationContext): String {

            val methodId = StringBuilder()
            methodId.append(declaration.typeTypeOrVoid().text)
            methodId.append(".")
            methodId.append(declaration.IDENTIFIER().text)

            // no parameters
            if (declaration.formalParameters().formalParameterList() == null) {
                methodId.append("()")
                return methodId.toString()
            }

            val paramTypes = declaration.formalParameters().formalParameterList().children
                .filter { it.text != "," }
                .map {

                    when (it) {
                        is JavaParser.FormalParameterContext -> {
                            it.typeType().text
                        }
                        is JavaParser.LastFormalParameterContext -> {
                            it.typeType().text
                        }
                        else -> {

                            // this can never be reached due to the grammar
                            ""
                        }
                    }
                }

            // single parameter
            if (paramTypes.size == 1) {
                methodId.append("(${paramTypes[0]})")
                return methodId.toString()
            }

            // multiple parameters, write params in comma separated format
            methodId.append("(")
            for (i in 0 until paramTypes.size - 1) methodId.append("${paramTypes[i]},")
            methodId.append("${paramTypes[paramTypes.lastIndex]})")

            return methodId.toString()
        }
    }
}
