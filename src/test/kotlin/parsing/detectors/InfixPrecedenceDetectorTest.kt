package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class InfixPrecedenceDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = InfixPrecedenceDetector(this.visitor, this.graph)
    }

    private fun assertAtom(atoms: List<List<Any>>) {
        kotlin.test.assertEquals(1, atoms.size)
        kotlin.test.assertEquals("INFIX_OPERATOR_PRECEDENCE", atoms[0][0])
    }

    @Test
    fun testPresent() {
        val atoms = runVisitorExpr("1 + 1/2")
        assertAtom(atoms)
    }

    @Test
    fun testInstanceof() {
        val atoms = runVisitorExpr("1 * 2 instanceof String")
        assertAtom(atoms)
    }

    @Test
    fun testBitshift() {
        val atoms = runVisitorExpr("1 * 2 >>> 1")
        assertAtom(atoms)
    }

    @Test
    fun testAbsent() {
        val atoms = runVisitorExpr("1 + (1/2)")
        assertTrue(atoms.isEmpty())
    }
}
