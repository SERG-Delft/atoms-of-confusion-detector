package input

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
        val myClassStream = InputStream("testdata/myClass.java")

        val myClass = File("testdata/myClass.java")
        inputResolver.resolveStreamsFromFile(myClass)
        assertEquals(inputResolver.streams, listOf(myClassStream))
    }

    @Test
    fun testResolveClassByDir() {
        val inputResolver = InputStreamResolver()
        val myClass = InputStream("testdata/myClass.java")

        val parentDir = File("testdata/")
        inputResolver.resolveStreamsFromFile(parentDir)
        assertEquals(inputResolver.streams, listOf(myClass))
    }

    @Test
    fun testResolveClassesRecursively() {
        Flags.RECURSIVELY_SEARCH_DIRECTORIES = true
        val inputResolver = InputStreamResolver()
        val myClass = InputStream("testdata/myClass.java")
        val nestedClass = InputStream("testdata/subdir/nestedClass.java")

        val parentDir = File("testdata/")
        inputResolver.resolveStreamsFromFile(parentDir)
        assertEquals(inputResolver.streams, listOf(myClass, nestedClass))
    }
}
