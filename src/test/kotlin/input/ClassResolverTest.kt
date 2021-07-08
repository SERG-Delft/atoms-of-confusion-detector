package input

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class ClassResolverTest {

    @BeforeEach
    fun setup() {
        Flags.RECURSIVELY_SEARCH_DIRECTORIES = false
        Flags.VERBOSE = false
    }

    @Test
    fun testResolveClass() {
        val classResolver = ClassResolver()
        val myClass = File("testdata/myClass.java")
        classResolver.resolveClasses(myClass)
        assertEquals(classResolver.classes, listOf(myClass))
    }

    @Test
    fun testResolveClassByDir() {
        val classResolver = ClassResolver()
        val myClass = File("testdata/myClass.java")
        val parentDir = File("testdata/")
        classResolver.resolveClasses(parentDir)
        assertEquals(classResolver.classes, listOf(myClass))
    }

    @Test
    fun testResolveClassesRecursively() {
        Flags.RECURSIVELY_SEARCH_DIRECTORIES = true
        val classResolver = ClassResolver()
        val myClass = File("testdata/myClass.java")
        val nestedClass = File("testdata/subdir/nestedClass.java")
        val parentDir = File("testdata/")
        classResolver.resolveClasses(parentDir)
        assertEquals(classResolver.classes, listOf(myClass, nestedClass))
    }
}
