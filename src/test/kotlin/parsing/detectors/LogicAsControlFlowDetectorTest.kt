package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LogicAsControlFlowDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = LogicAsControlFlowDetector(this.listener, this.graph)
    }

    @Test
    fun testBasic() {
        val atoms = runVisitorExpr("a == 2 || a++")
        assertAtom(atoms, "LOGIC_AS_CONTROL_FLOW")
    }

    @Test
    fun testPrefix() {
        val atoms = runVisitorExpr("a || ++a")
        assertAtom(atoms, "LOGIC_AS_CONTROL_FLOW")
    }

    @Test
    fun testNested() {
        val atoms = runVisitorExpr("a || (a || a++)")
        assertAtom(atoms, "LOGIC_AS_CONTROL_FLOW")
    }

    @Test
    fun testInClass() {
        val atoms = runVisitorFile("class A { void f() {if (a == 2 || a++) bar(); } }")
        assertAtom(atoms, "LOGIC_AS_CONTROL_FLOW")
    }
}
