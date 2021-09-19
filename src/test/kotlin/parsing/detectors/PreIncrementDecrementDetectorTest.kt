package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PreIncrementDecrementDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = PreIncrementDecrementDetector(this.listener, this.graph)
    }

    @Test
    fun testBasicIncrement() {
        val code = "b = ++a"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "PRE_INCREMENT_DECREMENT")
    }

    @Test
    fun testWithArithmetic() {
        val code = "v2 = 3 + ++v1"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "PRE_INCREMENT_DECREMENT")
    }

    @Test
    fun testIncrementInConditional() {
        val code = "++v1 == 0"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "PRE_INCREMENT_DECREMENT")
    }

    @Test
    fun testBasicDecrement() {
        val code = "b = --a"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "PRE_INCREMENT_DECREMENT")
    }

    @Test
    fun testWithArithmeticDecrement() {
        val code = "v2 = 3 + --v1"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "PRE_INCREMENT_DECREMENT")
    }

    @Test
    fun testDecrementInConditional() {
        val code = "--v1 == 0"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "PRE_INCREMENT_DECREMENT")
    }

    @Test
    fun testInClass() {
        val code = "class A { int a = 23; int b = ++a; public void foo() {} }"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "PRE_INCREMENT_DECREMENT")
    }

    @Test
    fun testInStatement() {
        val code = "--v1"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "PRE_INCREMENT_DECREMENT_AS_STATEMENT")
    }

    @Test
    fun testPostIncrementInMethodCall() {
        val code = "method(++a)"
        val atoms = runVisitorExpr(code)
        assertAtom(atoms, "PRE_INCREMENT_DECREMENT")
    }

    @Test
    fun testIncrementInForLoop() {
        val code = "class A { public void foo() { for (int i = 0; i < 10; ++i) {} } }"
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "PRE_INCREMENT_DECREMENT_IN_FOR_LOOP")
    }

    @Test
    fun testTildeNotDetected() {
        val code = "class A { int a = 32; public void foo() { ~a } }"
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testBangNotDetected() {
        val code = "class A { int a = 32; public void foo() { !a } }"
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }
}
