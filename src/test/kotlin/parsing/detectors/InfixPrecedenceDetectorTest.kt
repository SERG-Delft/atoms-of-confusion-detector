package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class InfixPrecedenceDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = InfixPrecedenceDetector(this.listener, this.graph)
    }

    @Test
    fun testPresent() {
        val atoms = runVisitorExpr("1 + 1/2")
        assertAtom(atoms, "INFIX_OPERATOR_PRECEDENCE")
    }

    @Test
    fun testInstanceof() {
        val atoms = runVisitorExpr("1 * 2 instanceof String")
        assertAtom(atoms, "INFIX_OPERATOR_PRECEDENCE")
    }

    @Test
    fun testBitshift() {
        val atoms = runVisitorExpr("1 * 2 >>> 1")
        assertAtom(atoms, "INFIX_OPERATOR_PRECEDENCE")
    }

    @Test
    fun testAbsent() {
        val atoms = runVisitorExpr("1 + (1/2)")
        assertTrue(atoms.isEmpty())
    }
}
