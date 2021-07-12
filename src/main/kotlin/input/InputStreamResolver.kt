package input

import java.io.File

class InputStreamResolver {

    val streams = mutableListOf<InputStream>()

    /**
     * Resolves the classes in a directory
     *
     * @param dir the directory
     * @param recursive if true classes are resolved recursively
     */
    private fun resolveDir(dir: File, recursive: Boolean) {

        val children = dir.listFiles() ?: return

        children.forEach {
            if (it.isFile) streams.add(InputStream(it.path))
            else if (it.isDirectory && recursive) resolveDir(it, true)
        }
    }

    /**
     * Resolves the classes given a file/directory
     *
     * @param file to a file/directory containing one or more java class
     */
    fun resolveStreamsFromFile(file: File) {
        if (file.isFile) streams.add(InputStream(file.path))
        else if (file.isDirectory) resolveDir(file, recursive = Flags.RECURSIVELY_SEARCH_DIRECTORIES)
    }
}
