package input

import java.io.File

class ClassResolver {

    val classes = mutableListOf<File>()

    /**
     * Resolves the classes in a directory
     *
     * @param dir the directory
     * @param recursive if true classes are resolved recursively
     */
    private fun resolveDir(dir: File, recursive: Boolean) {

        val children = dir.listFiles() ?: return

        children.forEach {
            if (it.isFile) classes.add(it)
            else if (it.isDirectory && recursive) resolveDir(it, true)
        }
    }

    /**
     * Resolves the classes given a file/directory
     *
     * @param path to a file/directory containing one or more java class
     */
    fun resolveClasses(path: File) {
        if (path.isFile) classes.add(path)
        else if (path.isDirectory) resolveDir(path, recursive = Flags.RECURSIVELY_SEARCH_DIRECTORIES)
    }
}
