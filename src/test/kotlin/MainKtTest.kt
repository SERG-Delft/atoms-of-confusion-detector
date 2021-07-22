import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.MissingArgument
import input.Settings
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        main(arrayOf("-v", "testdata/myClass.java"))
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testRecursiveFlagGiven() {
        main(arrayOf("-r", "testdata/myClass.java"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testVerboseAndRecursiveSettingsGiven() {
        main(arrayOf("-r", "-v", "testdata/myClass.java"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testNoSettingsGiven() {
        main(arrayOf("testdata/myClass.java"))
        assertFalse { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
        assertFalse { Settings.VERBOSE }
    }

    @Test
    fun testVerboseExplicitName() {
        main(arrayOf("testdata/myClass.java", "--verbose"))
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testVerboseCapitalName() {
        main(arrayOf("testdata/myClass.java", "-V"))
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testRecursiveExplicitName() {
        main(arrayOf("testdata/myClass.java", "--recursive"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testRecursiveCapitalName() {
        main(arrayOf("testdata/myClass.java", "-R"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
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
