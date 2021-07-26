package parsing.detectors

import org.junit.jupiter.api.BeforeEach

internal class RepurposedVariablesDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = RepurposedVariablesDetector(this.visitor, this.graph)
    }
}
