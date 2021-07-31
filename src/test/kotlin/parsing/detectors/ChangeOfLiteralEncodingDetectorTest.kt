package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ChangeOfLiteralEncodingDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = ChangeOfLiteralEncodingDetector(this.listener, this.graph)
    }

    @Test
    fun testBasic() {
        val atoms = runVisitorExpr("017")
        assertAtom(atoms, "CHANGE_OF_LITERAL_ENCODING")
    }
}
