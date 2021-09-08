package parsing.detectors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class RemoveIndentationDetectorTest : DetectorTest() {

    @BeforeEach
    fun setup() {
        this.detector = RemoveIndentationDetector(this.listener, this.graph)
    }

    @Test
    fun testNotApparentWhereElseBelongs() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                    boolean v1 = (2 != 3);
                    boolean v2 = false;
                    int v3 = 3;
                    if(v1)
                        if(v2)
                            v3 = v3 + 2;
                    else
                        v3 = v3 + 4; 
                }
            }
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "REMOVE_INDENTATION")
    }

    @Test
    fun testApparentWhereElseBelongsDoesNotTrigger() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                    boolean v1 = (2 != 3);
                    boolean v2 = false;
                    int v3 = 3;
                    if(v1)
                        if(v2)
                            v3 = v3 + 2;
                        else
                            v3 = v3 + 4; 
                }
            }
            """
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testNotApparentWhereElseBelongsMultipleNestings() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                    boolean v1 = (2 != 3);
                    boolean v2 = false;
                    int v3 = 3;
                    if(v1)
                        if(v2)
                            if(true == false)
                                v3 = v3 * 2;
                        else
                            v3 = v3 + 4; 
                }
            }
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "REMOVE_INDENTATION")
    }

    @Test
    fun testNotApparentWhereStatementAfterWhileBelongs() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                   int v1 = 5, v2 = 5;
                   while (v2 > 0)
                        v2--;
                        v1++;
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "REMOVE_INDENTATION")
    }

    @Test
    fun testNotApparentWhereStatementAfterForBelongs() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                   int v1 = 5, v2 = 5;
                   for (int i = 0; i < 10; i++)
                        v2--;
                        v1++;
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "REMOVE_INDENTATION")
    }

    @Test
    fun testApparentWhereStatementAfterWhileBelongsDoesNotTrigger() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                   int v1 = 5, v2 = 5;
                   while (v2 > 0)
                        v2--;
                   v1++;
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testApparentWhereStatementAfterForBelongsDoesNotTrigger() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                   int v1 = 5, v2 = 5;
                   for (int i = 0; i < 10; i++)
                        v2--;
                   v1++;
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testNotApparentWhereStatementAfterIfBelongs() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                    int v1 = 1, v2 = 2;
                    if (v1 > v2)
                    v2 = 1;
                    v1 = 2;
                }
            }    
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "REMOVE_INDENTATION")
    }

    @Test
    fun testApparentWhereStatementAfterIfBelongsDoesNotTrigger() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                    int v1 = 1, v2 = 2;
                    if (v1 > v2)
                        v2 = 1;
                    v1 = 2;
                }
            }    
            """
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }

    @Test
    fun testWhileStatementShouldBeIndented() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                   int v1 = 5, v2 = 5;
                   while (v2 > 0)
                   v2--;
                   v1++;
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "REMOVE_INDENTATION")
    }

    @Test
    fun testStatementAfterForLoopShouldBeIndented() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                   int v1 = 5, v2 = 5;
                   for (int i = 0; i < 10; i++)
                   v2--;
                   v1++;
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertAtom(atoms, "REMOVE_INDENTATION")
    }

    @Test
    fun testWhileBlockDoesNotTrigger() {
        val code =
            """
            class Snippet {
                public static void main(String[] args) {
                   int v1 = 5, v2 = 5;
                   while(v1 < 10) {
                      v2--;
                      v1++;
                   }
                }
            } 
            """
        val atoms = runVisitorFile(code)
        assertEquals(0, atoms.size)
    }
}
