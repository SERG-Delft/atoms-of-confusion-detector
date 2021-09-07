package parsing.symtab

import JavaParser
import org.antlr.v4.runtime.CharStreams
import org.junit.jupiter.api.Test
import parsing.ParsedFile

internal class SymtabUtilTest {

    private fun testMethodKeyGeneration(declaration: String, expected: String) {
        val file = ParsedFile(CharStreams.fromString(declaration))
        val tree = file.parser.methodDeclaration() as JavaParser.MethodDeclarationContext

        val actual = SymtabUtil.getMethodSymbolId(tree)

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun testNoParams() {
        testMethodKeyGeneration("void foo() {}", "void.foo()")
    }

    @Test
    fun testOneParam() {
        testMethodKeyGeneration("void foo(int param) {}", "void.foo(int)")
    }

    @Test
    fun testMultipleParams() {
        testMethodKeyGeneration("void foo(int p1, String p2) {}", "void.foo(int,String)")
    }
}
