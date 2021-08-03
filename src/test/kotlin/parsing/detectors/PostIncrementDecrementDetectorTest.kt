package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PostIncrementDecrementDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = PostIncrementDecrementDetector(this.listener, this.graph)
    }

    @Test
    fun testBasicIncrement() {
        val code = "b = a++"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "POST_INCREMENT_DECREMENT")
    }

    @Test
    fun testWithArithmetic() {
        val code = "v2 = 3 + v1++"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "POST_INCREMENT_DECREMENT")
    }

    @Test
    fun testIncrementInConditional() {
        val code = "v1++ == 0"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "POST_INCREMENT_DECREMENT")
    }

    @Test
    fun testBasicDecrement() {
        val code = "b = a--"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "POST_INCREMENT_DECREMENT")
    }

    @Test
    fun testWithArithmeticDecrement() {
        val code = "v2 = 3 + v1--"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "POST_INCREMENT_DECREMENT")
    }

    @Test
    fun testDecrementInConditional() {
        val code = "v1-- == 0"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "POST_INCREMENT_DECREMENT")
    }

    @Test
    fun testInClass() {
        val code = "class A { int a = 23; int b = a++; public void foo() {} }"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "POST_INCREMENT_DECREMENT")
    }

    @Test
    fun testIncrementInMethodCall() {
        val code = "methodCall(a++)"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "POST_INCREMENT_DECREMENT")
    }

    @Test
    fun testStatementDoesNotTrigger() {
        val code = "v1--"
        val atoms = runVisitorExpr(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testIncrementInForLoopDoesNotTrigger() {
        val code = "for (int i = 0; i < 10; i++) {}"
        val atoms = runVisitorExpr(code)
        assertEquals(0, atoms.size)
    }
}
