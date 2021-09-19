package github

import github.exceptions.InvalidDiffFileException
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException
import kotlin.math.absoluteValue

@Suppress("TooGenericExceptionCaught", "SwallowedException", "MagicNumber")
class DiffParser(diff: String) {

    private val addedLinesPerFile = mutableMapOf<String, MutableSet<Int>>()
    private val removedLinesPerFile = mutableMapOf<String, MutableSet<Int>>()

    val fromFileNames = mutableListOf<String>()
    val toFileNames = mutableListOf<String>()

    init {

        var currentFromFileName = ""
        var currentToFileName = ""

        var fromLineNumber = 0
        var toLineNumber = 0

        try {

            // for each line in the diff file, the goal is to find the line numbers
            // for lines added and lines removed for each file affected by the diff
            diff.split("\n").forEach { line ->

                // the first characters in a line determine its meaning all of the relevant
                // cases are accounted for in this when
                when {

                    line.startsWith("---") -> {

                        // get the file path
                        val filePath = line.substring(6)

                        // get save the from file
                        fromFileNames.add(filePath)

                        // update the fromFile
                        currentFromFileName = filePath
                        removedLinesPerFile[currentFromFileName] = mutableSetOf()
                    }

                    line.startsWith("+++") -> {

                        // get the file path
                        val filePath = line.substring(6)

                        // get save the to file
                        toFileNames.add(filePath)

                        // update the toFile
                        currentToFileName = filePath
                        addedLinesPerFile[currentToFileName] = mutableSetOf()
                    }

                    // entering a new hunk
                    line.startsWith("@@") -> {
                        val split = line.split(" ", ",")

                        // get the starting line numbers of the new hunk
                        fromLineNumber = Integer.parseInt(split[1]).absoluteValue
                        toLineNumber = Integer.parseInt(split[3]).absoluteValue
                    }

                    // line is unchanged
                    line.startsWith(" ") -> {
                        fromLineNumber++
                        toLineNumber++
                    }

                    // line is added
                    line.startsWith("+") -> {
                        // if the file is correctly formatted, this will never be null
                        addedLinesPerFile[currentToFileName]!!.add(toLineNumber)
                        toLineNumber++
                    }

                    // line is removed
                    line.startsWith("-") -> {
                        // if the file is correctly formatted, this will never be null
                        removedLinesPerFile[currentFromFileName]!!.add(fromLineNumber)
                        fromLineNumber++
                    }
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            throw InvalidDiffFileException()
        } catch (e: NullPointerException) {
            throw InvalidDiffFileException()
        }
    }

    fun addedLinesForFile(file: String): MutableSet<Int> {
        val addedLines = addedLinesPerFile[file]
        addedLines ?: return mutableSetOf()
        return addedLines
    }

    fun removedLinesForFile(file: String): MutableSet<Int> {
        val removedLines = removedLinesPerFile[file]
        removedLines ?: return mutableSetOf()
        return removedLines
    }
}
