package parsing.detectors

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ConditionalOperatorDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = ConditionalOperatorDetector(this.visitor, this.graph)
    }

    @Test
    fun testBasic() {
        val atoms = runVisitorExpr("(3 > 2) ? 4 : 5")
        assertAtom(atoms, "CONDITIONAL_OPERATOR")
    }

    @Test
    fun testInClass() {
        val atoms = runVisitorFile("class A { void f() {int a = (3 > 2) ? 4 : 5;} }")
        assertAtom(atoms, "CONDITIONAL_OPERATOR")
    }

    @Test
    fun testAtomNotPresent() {
        val atoms = runVisitorFile("class A { void f() {if (a == 2 || a++) bar(); } }")
        kotlin.test.assertEquals(0, atoms.size)
    }
}
