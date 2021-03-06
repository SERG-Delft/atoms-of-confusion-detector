import input.Settings
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class CliTest {

    // CLI tests:

    @BeforeEach
    fun setup() {
        Settings.VERBOSE = false
        Settings.RECURSIVELY_SEARCH_DIRECTORIES = false
    }

    @Test
    fun testVerboseFlagGiven() {
        main(arrayOf("-v", "files", "testdata/myClass.java"))
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testRecursiveFlagGiven() {
        main(arrayOf("files", "-r", "testdata/myClass.java"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testVerboseAndRecursiveSettingsGiven() {
        main(arrayOf("-v", "files", "-r", "testdata/myClass.java"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testNoSettingsGiven() {
        main(arrayOf("files", "testdata/myClass.java"))
        assertFalse { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
        assertFalse { Settings.VERBOSE }
    }

    @Test
    fun testVerboseExplicitName() {
        main(arrayOf("--verbose", "files", "testdata/myClass.java"))
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testVerboseCapitalName() {
        main(arrayOf("-V", "files", "testdata/myClass.java"))
        assertTrue { Settings.VERBOSE }
    }

    @Test
    fun testRecursiveExplicitName() {
        main(arrayOf("files", "testdata/myClass.java", "--recursive"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testRecursiveCapitalName() {
        main(arrayOf("files", "testdata/myClass.java", "-R"))
        assertTrue { Settings.RECURSIVELY_SEARCH_DIRECTORIES }
    }

    @Test
    fun testDirectory() {
        main(arrayOf("files", "testdata"))
    }
}
