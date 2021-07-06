import com.github.ajalt.clikt.core.MissingArgument
import input.Flags
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class MainKtTest {

    @BeforeEach
    fun setup() {
        Flags.VERBOSE = false
        Flags.RECURSIVELY_SEARCH_DIRECTORIES = false
    }

    @Test
    fun testMainNoArguments() {
        assertFailsWith<MissingArgument> {
            main(emptyArray())
        }
    }

    @Test
    fun testVerboseFlagGiven() {
        main(arrayOf("-v", "myClass.java"))
        assertTrue { Flags.VERBOSE }
    }

    @Test
    fun testRecursiveFlagGiven() {
        main(arrayOf("-r", "myClass.java"))
        assertTrue { Flags.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testVerboseAndRecursiveFlagsGiven() {
        main(arrayOf("-r", "-v", "myClass.java"))
        assertTrue { Flags.RECURSIVELY_SEARCH_DIRECTORIES }
        assertTrue { Flags.VERBOSE }
    }

    @Test
    fun testNoFlagsGiven() {
        main(arrayOf("myclass.Java"))
        assertFalse { Flags.RECURSIVELY_SEARCH_DIRECTORIES }
        assertFalse { Flags.VERBOSE }
    }

    @Test
    fun testVerboseExplicitName() {
        main(arrayOf("myClass.java", "--verbose"))
        assertTrue { Flags.VERBOSE }
    }

    @Test
    fun testVerboseCapitalName() {
        main(arrayOf("myClass.java", "-V"))
        assertTrue { Flags.VERBOSE }
    }

    @Test
    fun testRecursiveExplicitName() {
        main(arrayOf("myClass.java", "--recursive"))
        assertTrue { Flags.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testRecursiveCapitalName() {
        main(arrayOf("myClass.java", "-R"))
        assertTrue { Flags.RECURSIVELY_SEARCH_DIRECTORIES }
    }
}
