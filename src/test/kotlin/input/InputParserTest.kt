package input

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class InputParserTest {

    @BeforeEach
    fun setup() {
        Settings.RECURSIVELY_SEARCH_DIRECTORIES = false
        Settings.VERBOSE = false
    }

    @Test
    fun testResolveClass() {
        val inputResolver = InputParser()

        val myClass = File("testdata/myClass.java")
        inputResolver.resolveFile(myClass)

        val expected = listOf("testdata/myClass.java")
        val actual = inputResolver.files.map { it.name.replace("\\", "/") }
        assertEquals(expected, actual)
    }

    @Test
    fun testResolveClassByDir() {
        val inputResolver = InputParser()
        val parentDir = File("testdata/subdir")
        inputResolver.resolveFile(parentDir)

        val actual = listOf("testdata/subdir/nestedClass.java")
        val expected = inputResolver.files.map { it.name.replace("\\", "/") }
        assertEquals(expected, actual)
    }
}
