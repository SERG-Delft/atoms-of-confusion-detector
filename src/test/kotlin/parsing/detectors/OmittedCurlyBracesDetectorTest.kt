package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class OmittedCurlyBracesDetectorTest : DetectorTest() {

    private fun assertPresent(atoms: List<List<Any>>) {
        assertAtom(atoms, "OMITTED_CURLY_BRACES")
    }

    private fun assertAbsent(atoms: List<List<Any>>) {
        assertTrue(atoms.isEmpty())
    }

    @BeforeEach
    fun setup() {
        this.detector = OmittedCurlyBracesDetector(this.listener, this.graph)
    }

    @Test
    fun testForPresent() {
        val atoms = runVisitorBlock("{ for(;;) foo(); bar(); }")
        assertPresent(atoms)
    }

    @Test
    fun testForNewline() {
        val atoms = runVisitorBlock("{ for(;;) foo();\nbar(); }")
        assertAbsent(atoms)
    }

    @Test
    fun testForLastStatement() {
        val atoms = runVisitorBlock("{ for(;;) foo(); }")
        assertAbsent(atoms)
    }

    @Test
    fun testForCurlyPresent() {
        val atoms = runVisitorBlock("{ for(;;) {foo();} bar(); }")
        assertAbsent(atoms)
    }

    @Test
    fun testWhilePresent() {
        val atoms = runVisitorBlock("{ while(true) foo(); bar(); }")
        assertPresent(atoms)
    }

    @Test
    fun testWhileLastStatement() {
        val atoms = runVisitorBlock("{ while(true) foo(); }")
        assertAbsent(atoms)
    }

    @Test
    fun testWhileNewline() {
        val atoms = runVisitorBlock("{ while (true) foo(); \nbar(); }")
        assertAbsent(atoms)
    }

    @Test
    fun testWhileBrackets() {
        val atoms = runVisitorBlock("{ while (true) { foo(); } \nbar(); }")
        assertAbsent(atoms)
    }

    @Test
    fun testIfPresent() {
        val atoms = runVisitorBlock("{ if (true) foo(); bar(); }")
        assertPresent(atoms)
    }

    @Test
    fun testIfLastStatemet() {
        val atoms = runVisitorBlock("{ if (true) foo(); }")
        assertAbsent(atoms)
    }

    @Test
    fun testIfNewline() {
        val atoms = runVisitorBlock("{ if (true) foo();\nbar(); }")
        assertAbsent(atoms)
    }

    @Test
    fun testIfBrackets() {
        val atoms = runVisitorBlock("{ if (true) { foo(); } dar(); }")
        assertAbsent(atoms)
    }

    @Test
    fun testElsePresent() {
        val atoms = runVisitorBlock("{ if (true) foo(); else bar(); dar(); }")
        assertPresent(atoms)
    }

    @Test
    fun testElseLastStatement() {
        val atoms = runVisitorBlock("{ if (true) foo(); else bar(); }")
        assertAbsent(atoms)
    }

    @Test
    fun testElseNewline() {
        val atoms = runVisitorBlock("{ if (true) foo(); else bar();\ndar(); }")
        assertAbsent(atoms)
    }

    @Test
    fun testElseBrackets() {
        val atoms = runVisitorBlock("{ if (true) foo(); else { bar(); } dar(); }")
        assertAbsent(atoms)
    }
}
