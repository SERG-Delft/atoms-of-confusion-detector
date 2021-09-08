package input

import org.antlr.v4.runtime.CharStreams
import parsing.ParsedFile
import java.io.File

class InputParser {

    val files = mutableListOf<ParsedFile>()

    /**
     * Resolves the classes in a directory
     *
     * @param dir the directory
     * @param recursive if true classes are resolved recursively
     */
    private fun resolveDir(dir: File, recursive: Boolean) {

        val children = dir.listFiles() ?: return

        children.forEach {
            if (it.isFile) {
                val parsedFile = ParsedFile(CharStreams.fromFileName(it.path))
                files.add(parsedFile)
            } else if (it.isDirectory && recursive) resolveDir(it, true)
        }
    }

    /**
     * Resolves the classes given a file/directory
     *
     * @param file to a file/directory containing one or more java classes
     */
    fun resolveFile(file: File) {
        if (file.isFile) {
            val parsedFile = ParsedFile(CharStreams.fromFileName(file.path))
            files.add(parsedFile)
        } else if (file.isDirectory) {
            resolveDir(file, recursive = Settings.RECURSIVELY_SEARCH_DIRECTORIES)
        }
    }
}
