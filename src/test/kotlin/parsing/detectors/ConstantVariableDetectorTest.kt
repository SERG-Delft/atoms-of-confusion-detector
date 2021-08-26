package parsing.detectors

import org.antlr.v4.runtime.CharStreams
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import parsing.ParsedFile
import kotlin.test.assertEquals

internal class ConstantVariableDetectorTest : DetectorTest() {

    fun testGetIdentifiersInExpression(expr: String, vararg expected: String) {
        val file = ParsedFile(CharStreams.fromString(expr))
        val tree = file.parser.expression() as JavaParser.ExprInfixContext

        val d = ConstantVariableDetector(listener, graph)
        listener.parsedFile = file
        listener.fileName = "f1"
        val actual = d.getVariablesInExpression(tree).map { it.text }.toSet()

        assertEquals(expected.toSet(), actual)
    }

    @BeforeEach
    fun setup() {
        this.detector = ConstantVariableDetector(this.listener, this.graph)
    }

    @Test
    fun testConstantAssignment() {
        val code =
            "class A { " +
                "   public static void main(String[] args) {" +
                "       int v1 = 5; " +
                "       int v2 = v1;" +
                "       System.out.println(v2);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "CONSTANT_VARIABLES")
    }

    @Test
    fun testConstantAssignmentIdentifierDoesNotExit() {
        val code =
            "class A { " +
                "   public static void main(String[] args) {" +
                "       int v2 = v3;" +
                "       System.out.println(v2);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testSimpleInfix() {
        val code =
            "class A { " +
                "   public static void main(String[] args) {" +
                "       int v1 = 5; " +
                "       int v2 = v1 + 2; " +
                "       System.out.println(v2);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "CONSTANT_VARIABLES")
    }

    @Test
    fun testComplexInfix() {
        val code =
            "class A { " +
                "   public static void main(String[] args) {" +
                "       int v1 = 5; " +
                "       int v2 = 1 + 2 + (5 / (v1 + 4 * 4)); " +
                "       System.out.println(v2);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "CONSTANT_VARIABLES")
    }

    @Test
    fun testComplexInfixNotConstant() {
        val code =
            "class A { " +
                "   public static void main(String[] args) {" +
                "       int v1 = 5; " +
                "       int v3 = magicMethod();" +
                "       int v2 = 1 + 2 + (5 / (v1 + 4 * 4)) + v3; " +
                "       System.out.println(v2);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testInfixExpressionOnItsOwnDoesNotTrigger() {
        val code =
            "class A { " +
                "   public static void main(String[] args) {" +
                "       int v1 = 5; " +
                "       int v3 = magicMethod();" +
                "       1 + 2 + (5 / (v1 + 4 * 4)) + v3; " +
                "       System.out.println(v1);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testArrayInitializerDoesNotTrigger() {
        val code =
            "class A { " +
                "   public static void main(String[] args) {" +
                "       int v1 = 3;" +
                "       int[] v2 = {1,2,v1};" +
                "       System.out.println(v2);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testNormalDeclarationDoesNotTrigger() {
        val code =
            "class A { " +
                "   public static void main(String[] args) {" +
                "       int v1 = 3 + 2; " +
                "       System.out.println(v1);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testPrefixConstantAssignment() {
        val code =
            "class A { " +
                "   public static void main(String[] args) {" +
                "       int v1 = 3 + 2; " +
                "       int v2 = ++v1" +
                "       System.out.println(v2);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "CONSTANT_VARIABLES")
    }

    @Test
    fun testPostfixConstantAssignment() {
        val code =
            "class A { " +
                "   public static void main(String[] args) {" +
                "       int v1 = 3 + 2; " +
                "       int v2 = v1++" +
                "       System.out.println(v2);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "CONSTANT_VARIABLES")
    }

    @Test
    fun testSimpleInfixMultiplication() {
        val code =
            "class A {" +
                "   public static void main(String[] args) {" +
                "       int v1 = 2; int v2 = 2*v1; System.out.println(v2);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "CONSTANT_VARIABLES")
    }

    @Test
    fun testInStatement() {
        val code =
            "class A {" +
                "   public static void main(String[] args) {" +
                "       int v1 = 2;" +
                "       v1 = 5;" +
                "       System.out.println(2.5*v1);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "CONSTANT_VARIABLES")
    }

    @Test
    fun testDirectMultiplicationDoesNotTrigger() {
        val code =
            "class A {" +
                "   public static void main(String[] args) {" +
                "       int v1 = 2; " +
                "       System.out.println(2.5*2);" +
                "   }" +
                "}"
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testClassFieldIsTheConstant() {
        val code = "class A {" +
            "int v1 = 2;" +
            "   public static void main(String[] args) {" +
            "       int v2 = 2*v1;" +
            "       System.out.println(v2);" +
            "   }" +
            "}"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "CONSTANT_VARIABLES")
    }

    @Test
    fun testAtomIsInBetweenClassFields() {
        val code = "class A {" +
            "int v1 = 2;" +
            "int v2 = v1;" +
            "}"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "CONSTANT_VARIABLES")
    }

    @Test
    fun testVarResolvingSimple() {
        testGetIdentifiersInExpression("a + b", "a", "b")
    }

    @Test
    fun testVarResolvingMemberAccess() {
        testGetIdentifiersInExpression("a + b.foo", "a")
    }

    @Test
    fun testVarResolvingMethodCall() {
        testGetIdentifiersInExpression("a + b.foo()", "a")
    }

    @Test
    fun testVarNestedMember() {
        testGetIdentifiersInExpression("a + b.a.b", "a")
    }
}
