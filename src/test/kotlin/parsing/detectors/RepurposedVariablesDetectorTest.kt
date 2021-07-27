package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class RepurposedVariablesDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = RepurposedVariablesDetector(this.visitor, this.graph)
    }


    @Test
    fun testClass() {
        val code = "class A { public void foo() {int a = 42; a = 43;} }"
        val atoms = this.runVisitorFile(code)
        assertAtom(atoms, "REPURPOSED_VARIABLES")
    }

    @Test
    fun testNotRepurposed() {
        val code = "class A { public void foo() {int a = 42; a = a + 43;} }"
        val atoms = this.runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testNestedForLoop() {
        val code = "class A { " +
                "   public void foo() { " +
                "       for(int i = 0; i < 10; i++) {" +
                "           for(int j = 0; j < 10; i++) {" +
                "           }" +
                "       } " +
                "   } " +
                "}"
        val atoms = this.runVisitorFile(code)
        assertAtom(atoms, "REPURPOSED_VARIABLES")
    }

}
