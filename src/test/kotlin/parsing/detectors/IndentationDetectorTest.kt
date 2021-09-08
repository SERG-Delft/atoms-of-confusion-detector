package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class IndentationDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = IndentationDetector(this.listener, this.graph)
    }

    @Test
    fun testExpressionIsIndentedWithoutReason() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                    int v1 = 0;
                    int v2 = 2;
                    if (v1 > 0) {v2++;}
                        v2 = 4;
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "INDENTATION")
    }

    @Test
    fun testExpressionIsIndentedWithoutReasonWhileLoop() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                    int v1 = 0;
                    int v2 = 2;
                    while (v1 > 0) {v1--;}
                        v2 = 4;
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "INDENTATION")
    }

    @Test
    fun testExpressionIsIndentedWithoutReasonForLoop() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                    int v1 = 0;
                    int v2 = 2;
                    for (int i = 0; i < 10; i++) {v1--;}
                        v2 = 4;
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "INDENTATION")
    }

    @Test
    fun testExpressionIsIndentedWithoutReasonWithExtraNestingBefore() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                    int v1 = 0;
                    int v2 = 2;
                    if (v1 > 0) {
                        v2 = 2;
                    }
                        v2 = v2 * 3;
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "INDENTATION")
    }

    @Test
    fun testExpressionClosingBracketsMismatchInIndentation() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                    int v1 = 2;
                    int v2 = 0;
                    int v3 = 3;
                    
                    if (v1 > 0) {
                        if (v2 > 0) {
                            v3 = v3 + 2;
                    } else {
                        v3 = v3 + 4;
                    }
                    }
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "INDENTATION")
    }

    @Test
    fun testProperlyAlignedBracketsDoNotTrigger() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                    int v1 = 2;
                    int v2 = 0;
                    int v3 = 3;
                    
                    if (v1 > 0) {
                        if (v2 > 0) {
                            v3 = v3 + 2;
                        } else {
                            v3 = v3 + 4;
                        }
                    }
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }
}
