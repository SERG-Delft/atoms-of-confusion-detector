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
        assertEquals(1, atoms.size)
        assertEquals("CONDITIONAL_OPERATOR", atoms[0][0])
    }

    @Test
    fun testInClass() {
        val atoms = runVisitorFile("class A { void f() {int a = (3 > 2) ? 4 : 5;} }")
        assertEquals(1, atoms.size)
        assertEquals("CONDITIONAL_OPERATOR", atoms[0][0])
    }

    @Test
    fun testAtomNotPresent() {
        val atoms = runVisitorFile("class A { void f() {if (a == 2 || a++) bar(); } }")
        kotlin.test.assertEquals(0, atoms.size)
    }
}
