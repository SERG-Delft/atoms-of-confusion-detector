import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.MissingArgument
import input.Flags
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        main(arrayOf("-v", "testdata/myClass.java"))
        assertTrue { Flags.VERBOSE }
    }

    @Test
    fun testRecursiveFlagGiven() {
        main(arrayOf("-r", "testdata/myClass.java"))
        assertTrue { Flags.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testVerboseAndRecursiveFlagsGiven() {
        main(arrayOf("-r", "-v", "testdata/myClass.java"))
        assertTrue { Flags.RECURSIVELY_SEARCH_DIRECTORIES }
        assertTrue { Flags.VERBOSE }
    }

    @Test
    fun testNoFlagsGiven() {
        main(arrayOf("testdata/myClass.java"))
        assertFalse { Flags.RECURSIVELY_SEARCH_DIRECTORIES }
        assertFalse { Flags.VERBOSE }
    }

    @Test
    fun testVerboseExplicitName() {
        main(arrayOf("testdata/myClass.java", "--verbose"))
        assertTrue { Flags.VERBOSE }
    }

    @Test
    fun testVerboseCapitalName() {
        main(arrayOf("testdata/myClass.java", "-V"))
        assertTrue { Flags.VERBOSE }
    }

    @Test
    fun testRecursiveExplicitName() {
        main(arrayOf("testdata/myClass.java", "--recursive"))
        assertTrue { Flags.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testRecursiveCapitalName() {
        main(arrayOf("testdata/myClass.java", "-R"))
        assertTrue { Flags.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testDirectory() {
        main(arrayOf("testdata"))
    }

    @Test
    fun testInvalidFile() {
        assertThrows<BadParameterValue> {
            main(arrayOf("testdata/missing.java"))
        }
    }
}
