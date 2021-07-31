package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class OmittedCurlyBracesDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = OmittedCurlyBracesDetector(this.listener, this.graph)
    }

    @Test
    fun testForBasic() {
        val atoms = runVisitorBlock("{ for(;;) foo(); bar(); }")
        assertAtom(atoms, "OMITTED_CURLY_BRACES")
    }

    @Test
    fun testForWithNewline() {
        val atoms = runVisitorBlock("{ for(;;) foo();\nbar(); }")
        assertTrue(atoms.isEmpty())
    }

    @Test
    fun testForWithNewlineAndSpaces() {
        val atoms = runVisitorBlock("{ for(;;) foo();  \nbar(); }")
        assertTrue(atoms.isEmpty())
    }

    @Test
    fun testForLastStatement() {
        val atoms = runVisitorBlock("{ for(;;) foo(); }")
        assertTrue(atoms.isEmpty())
    }

    @Test
    fun testWhileBasic() {
        val atoms = runVisitorBlock("{ while(true) foo(); bar(); }")
        assertAtom(atoms, "OMITTED_CURLY_BRACES")
    }

    @Test
    fun testWhileithNewline() {
        val atoms = runVisitorBlock("{ while(true) foo();\nbar(); }")
        assertTrue(atoms.isEmpty())
    }

    @Test
    fun testWhileWithNewlineAndSpaces() {
        val atoms = runVisitorBlock("{ while(true) foo();  \nbar(); }")
        assertTrue(atoms.isEmpty())
    }

    @Test
    fun testWhileLastStatement() {
        val atoms = runVisitorBlock("{ while(true) foo(); }")
        assertTrue(atoms.isEmpty())
    }

    @Test
    fun testIf() {
        val atoms = runVisitorBlock("{ if (true) foo(); else bar(); }")
        assertTrue(atoms.isEmpty())
    }
}
