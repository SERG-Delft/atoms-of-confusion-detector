package input

import org.antlr.v4.runtime.CharStreams
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class InputStreamResolverTest {

    @BeforeEach
    fun setup() {
        Flags.RECURSIVELY_SEARCH_DIRECTORIES = false
        Flags.VERBOSE = false
    }

    @Test
    fun testResolveClass() {
        val inputResolver = InputStreamResolver()
        val myClassStream = CharStreams.fromFileName("testdata/myClass.java")

        val myClass = File("testdata/myClass.java")
        inputResolver.resolveStreamsFromFile(myClass)

        val expected = listOf(myClassStream).map { it.toString() }
        val actual = inputResolver.streams.map { it.toString() }
        assertEquals(expected, actual)
    }

    @Test
    fun testResolveClassByDir() {
        val inputResolver = InputStreamResolver()
        val myClass = CharStreams.fromFileName("testdata/myClass.java")
        val parentDir = File("testdata/")
        inputResolver.resolveStreamsFromFile(parentDir)

        val actual = inputResolver.streams.map { it.toString() }
        val expected = listOf(myClass).map { it.toString() }
        assertEquals(expected, actual)
    }

    @Test
    fun testResolveClassesRecursively() {
        Flags.RECURSIVELY_SEARCH_DIRECTORIES = true
        val inputResolver = InputStreamResolver()
        val myClass = CharStreams.fromFileName("testdata/myClass.java")
        val nestedClass = CharStreams.fromFileName("testdata/subdir/nestedClass.java")
        val parentDir = File("testdata/")
        inputResolver.resolveStreamsFromFile(parentDir)

        val actual = inputResolver.streams.map { it.toString() }
        val expected = listOf(myClass, nestedClass).map { it.toString() }
        assertEquals(expected, actual)
    }
}
