package parsing

import JavaParser
import org.antlr.symtab.PrimitiveType
import org.antlr.symtab.Symbol
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import output.graph.ConfusionGraph
import parsing.detectors.Detector
import parsing.detectors.Visit
import parsing.symtab.symbols.AtomsBaseSymbol
import parsing.symtab.symbols.AtomsClassFieldSymbol
import parsing.symtab.symbols.AtomsLocalVariableSymbol
import parsing.symtab.symbols.AtomsParameterSymbol
import parsing.symtab.types.ReferenceType

internal class AtomsListenerTest {

    private val listener = AtomsListener()

    @BeforeEach
    fun setup() {
        listener.fileName = "f1"
    }

    @Test
    fun testClassFieldNull() {
        val code = "class A { int classField; void fun(int param) { int localVar = 6; foo(); param++; } } }"
        val (walker, graph, file) = parse(code)
        val expected = AtomsClassFieldSymbol("classField", PrimitiveType("int"), null)
        val detector = TestDetector(listener, graph, expected, "classField")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testClassField() {
        val code = "class A { int classField = 42; void fun(int param) { int localVar = 6; foo(); param++; } }"
        val (walker, graph, file) = parse(code)
        val expected = AtomsClassFieldSymbol("classField", PrimitiveType("int"), "42")
        val detector = TestDetector(listener, graph, expected, "classField")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testClassFieldReassignment() {
        val code =
            "class A { int classField = 42; void fun(int param) {classField = 43; int localVar = 6; foo(); param++; } }"
        val (walker, graph, file) = parse(code)
        val expected = AtomsClassFieldSymbol("classField", PrimitiveType("int"), "43")
        val detector = TestDetector(listener, graph, expected, "classField")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testClassFieldShadows() {
        val code = "class A { int classField = 42; void fun(int param) {" +
            "boolean classField = true; int localVar = 6; foo(); param++; " + "} " +
            "}"
        val (walker, graph, file) = parse(code)
        val expected = AtomsLocalVariableSymbol("classField", PrimitiveType("boolean"), "true")
        val detector = TestDetector(listener, graph, expected, "classField")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testClassFieldShadowedInOneMethod() {
        val code = "class A { int classField = 42; void fun(int param) {" +
            "boolean classField = true; int localVar = 6; foo();" +
            "} void bar(int a) {a++;} }"
        val (walker, graph, file) = parse(code)
        val expected = AtomsClassFieldSymbol("classField", PrimitiveType("int"), "42")
        val detector = TestDetector(listener, graph, expected, "classField")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testMethodParameter() {
        val code =
            "class A { int classField = 42; void fun(int param) {" +
                "boolean classField = true; int localVar = 6; foo(); param++; } " +
                "}"
        val (walker, graph, file) = parse(code)
        val expected = AtomsParameterSymbol("param", PrimitiveType("int"))
        val detector = TestDetector(listener, graph, expected, "param")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testLocalVariableDeclared() {
        val code =
            "class A { int classField = 42; void fun(int param) {" +
                "boolean classField = true; int localVar = 6; foo(); param++; " +
                "} }"
        val (walker, graph, file) = parse(code)
        val expected = AtomsLocalVariableSymbol("localVar", PrimitiveType("int"), "6")
        val detector = TestDetector(listener, graph, expected, "localVar")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testLocalVariableReAssigned() {
        val code =
            "class A { int classField = 42; void fun(int param) {" +
                "boolean classField = true; int localVar = 6; localVar = 10; foo(); param++; } " +
                "}"
        val (walker, graph, file) = parse(code)
        val expected = AtomsLocalVariableSymbol("localVar", PrimitiveType("int"), "10")
        val detector = TestDetector(listener, graph, expected, "localVar")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testLocalScopeShadows() {
        val code =
            "class A { int classField = 42; void fun(int param) {" +
                "int localVar = 6; {boolean localVar = false; param++; } " +
                "} }"
        val (walker, graph, file) = parse(code)
        val expected = AtomsLocalVariableSymbol("localVar", PrimitiveType("boolean"), "false")
        val detector = TestDetector(listener, graph, expected, "localVar")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testConstructorShadowsField() {
        val code =
            """
            private static class Option {
                private String id;
                private int minSdk;
                private int minBuild;
                
                public Option(String id, int minSdk, int minBuild) {
                    this.id = id;
                    this.minSdk = minSdk;
                    this.minBuild = minBuild;
                    this.minSdk++;
                }
            } 
            """
        val (walker, graph, file) = parse(code)
        val expected = AtomsParameterSymbol("id", ReferenceType("String"))
        val detector = TestDetector(listener, graph, expected, "id")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testMethodParamShadowsField() {
        val code =
            """
            private static class Option {
                private String id;
                private int minSdk;
                private int minBuild;
                
                public void magicFoo(String id, int minSdk, int minBuild) {
                    this.id = id;
                    this.minSdk = minSdk;
                    this.minBuild = minBuild;
                    this.minSdk++;
                }
            } 
            """
        val (walker, graph, file) = parse(code)
        val expected = AtomsParameterSymbol("minSdk", PrimitiveType("int"))
        val detector = TestDetector(listener, graph, expected, "minSdk")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testLocalScopePopped() {
        val code =
            "class A { int classField = 42; void fun(int param) {" +
                "int localVar = 6; {boolean localVar = false;} param++; } " +
                "}"
        val (walker, graph, file) = parse(code)
        val expected = AtomsLocalVariableSymbol("localVar", PrimitiveType("int"), "6")
        val detector = TestDetector(listener, graph, expected, "localVar")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testDoubleFor() {
        val code = """
       void foo() {

            for (int i = 1; i >= 0; --i) {
            }

            for (int i = 1; i >= 0; --i) {
            }
    }
        """

        // no assertion, test checks for lack of exceptions
        val (walker, graph, file) = parse(code)
        walker.walk(listener, file.parser.methodDeclaration())
    }

    @Test
    fun testDoubleEnhancedFor() {
        val code = """
       void foo() {

            for (int i : arr) {
                if (true) a();
            }
            
            while (true) {}

            for (int i : arr) {
            }
    }
        """

        val (walker, graph, file) = parse(code)
        val expected = AtomsClassFieldSymbol("classField", PrimitiveType("int"), null)
        val detector = TestDetector(listener, graph, expected, "classField")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.methodDeclaration())
    }

    @Test
    // investigate further
    fun testDoubleMethod() {
        val code =
            """
            private static class Option {
                public void foo(int a) {}
                public void bar(int a) {}
            } 
            """
        val (walker, graph, file) = parse(code)
        val expected = AtomsParameterSymbol("minSdk", PrimitiveType("int"))
        val detector = TestDetector(listener, graph, expected, "minSdk")
        listener.registerDetector(detector)
        walker.walk(listener, file.parser.compilationUnit())
    }

    @Test
    fun testAnonymousInnerClass() {
        val code =
            """
            class A {
            
                Obj b = new Obj(1, 2) {
                    @Override
                    public void foo(int a) {
                        a++;
                    }
                };
                
                Obj c = new Obj(1, 2) {
                    @Override
                    public void foo(int a) {
                        a--;
                    }
                };
            
            }
            """
        val (walker, graph, file) = parse(code)
        walker.walk(listener, file.parser.compilationUnit())
    }

    private fun parse(code: String): Triple<ParseTreeWalker, ConfusionGraph, ParsedFile> {
        val walker = ParseTreeWalker()
        val graph = ConfusionGraph(listOf("f1"))
        val file = ParsedFile(
            CharStreams.fromString(
                code
            )
        )
        return Triple(walker, graph, file)
    }
}

/**
 * This class is used to assert the scope during
 * the traversal of the program.
 */
@Visit(JavaParser.ExprPostfixContext::class)
class TestDetector(
    override val listener: AtomsListener,
    override val graph: ConfusionGraph,
    private val expected: Symbol,
    private val nameOfField: String
) : Detector(listener, graph) {
    override fun detect(ctx: JavaParser.ExprPostfixContext) {
        val v = listener.currentScope?.resolve(nameOfField)
        assertEquals(expected, v)
        if (expected is AtomsBaseSymbol) {
            assertEquals(expected.value, (v as AtomsBaseSymbol).parseTreeNodeValue?.text)
        }
    }
}
