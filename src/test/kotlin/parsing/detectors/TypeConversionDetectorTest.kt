package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class TypeConversionDetectorTest : DetectorTest() {

    private fun assertPresent(atoms: List<List<Any>>) {
        assertAtom(atoms, "TYPE_CONVERSION")
    }

    private fun assertAbsent(atoms: List<List<Any>>) {
        assertTrue(atoms.isEmpty())
    }

    @BeforeEach
    fun setup() {
        this.detector = TypeConversionDetector(this.listener, this.graph)
    }

    @Test
    fun testPresent() {
        val atoms = runVisitorExpr("(int) 1.9")
        assertPresent(atoms)
    }

    @Test
    fun testAbsent() {
        val atoms = runVisitorExpr("(double) 1.9")
        assertAbsent(atoms)
    }
}
