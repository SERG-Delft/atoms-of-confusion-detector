package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class LogicAsControlFlowDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = LogicAsControlFlowDetector(this.visitor, this.graph)
    }

    @Test
    fun testBasic() {
        val atoms = runVisitorExpr("a == 2 || a++")

        assertEquals(1, atoms.size)
        assertEquals("LOGIC_AS_CONTROL_FLOW", atoms[0][0])
    }

    @Test
    fun testPrefix() {
        val atoms = runVisitorExpr("a || ++a")

        assertEquals(1, atoms.size)
        assertEquals("LOGIC_AS_CONTROL_FLOW", atoms[0][0])
    }

    @Test
    fun testNested() {
        val atoms = runVisitorExpr("a || (a || a++)")

        assertEquals(1, atoms.size)
        assertEquals("LOGIC_AS_CONTROL_FLOW", atoms[0][0])
    }

    @Test
    fun testInClass() {
        val atoms = runVisitorFile("class A { void f() {if (a == 2 || a++) bar(); } }")

        assertEquals(1, atoms.size)
        assertEquals("LOGIC_AS_CONTROL_FLOW", atoms[0][0])
    }
}
