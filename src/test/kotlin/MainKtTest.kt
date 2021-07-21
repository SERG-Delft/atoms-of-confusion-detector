import com.github.ajalt.clikt.core.MissingArgument
import input.Settings
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class MainKtTest {

    @BeforeEach
    fun setup() {
        Settings.VERBOSE = false
        Settings.RECURSIVELY_SEARCH_DIRECTORIES = false
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
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testRecursiveFlagGiven() {
        main(arrayOf("-r", "myClass.java"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testVerboseAndRecursiveFlagsGiven() {
        main(arrayOf("-r", "-v", "myClass.java"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testNoFlagsGiven() {
        main(arrayOf("myclass.Java"))
        assertFalse { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
        assertFalse { Settings.VERBOSE }
    }

    @Test
    fun testVerboseExplicitName() {
        main(arrayOf("myClass.java", "--verbose"))
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testVerboseCapitalName() {
        main(arrayOf("myClass.java", "-V"))
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testRecursiveExplicitName() {
        main(arrayOf("myClass.java", "--recursive"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testRecursiveCapitalName() {
        main(arrayOf("myClass.java", "-R"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
    }
}
