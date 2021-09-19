package github

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class DiffParserTest {

    val pathToDiff = "testdata/diff/exampleDiff.diff"
    val diffContent = File(pathToDiff).readText()
    val diffParser = DiffParser(diffContent)

    @Test
    fun testToFilesAffected() {
        val expected = listOf(
            "atoms-of-confusion-analysis.csv",
            "src/main/java/JavaParser.g4",
            "src/main/java/JavaParser.java",
            "src/main/kotlin/parsing/AtomsListener.kt",
            "src/main/kotlin/parsing/detectors/Detector.kt",
            "src/main/kotlin/parsing/detectors/TypeConversionDetector.kt",
            "src/test/kotlin/parsing/detectors/TypeConversionDetectorTest.kt",
        )
        val toFiles = diffParser.getToFiles().map { it.replace("\\", "/").replace("\r", "\n") }
        assertEquals(expected, toFiles)
    }

    @Test
    fun testGetAddedLines() {
        val fileName = "src/main/kotlin/parsing/AtomsListener.kt"
        val expected = mutableSetOf(
            49, 50, 51, 52
        )
        val linesAdded = diffParser.addedLinesForFile(fileName)
        assertEquals(expected, linesAdded)
    }

    @Test
    fun testGetRemovedLines() {
        val fileName = "src/main/java/JavaParser.java"
        val expected = mutableSetOf(7651, 8270)
        val linesRemoved = diffParser.removedLinesForFile(fileName)
        assertEquals(expected, linesRemoved)
    }

    @Test
    fun testGetAddedLinesFromFileWithRemoval() {
        val fileName = "src/main/java/JavaParser.java"
        val expected = mutableSetOf(7646, 7649, 8271)
        val linesAdded = diffParser.addedLinesForFile(fileName)
        assertEquals(expected, linesAdded)
    }
}
