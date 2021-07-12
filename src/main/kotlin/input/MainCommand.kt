package input

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import java.nio.file.Path

/**
 * This is the class representing the command that runs when the tool is invoked.
 * It implements the parsing of the CLI arguments and options.
 */
class MainCommand : CliktCommand(help = "Analyze the provided files for atoms of confusion") {

    private val recursiveFlag by option(
        "-r", "--recursive", "-R",
        help = "This flag tells the tool to recursively search any input directory for Java files"
    ).flag(default = Flags.RECURSIVELY_SEARCH_DIRECTORIES)
    private val verboseFlag by option(
        "-v", "--verbose", "-V",
        help = "This flag tells the tool to print the results of its analysis on the console"
    ).flag(default = Flags.VERBOSE)

    private val sources: List<Path> by argument()
        .path(mustExist = true, mustBeReadable = true)
        .multiple(required = true)

    override fun run() {
        Flags.RECURSIVELY_SEARCH_DIRECTORIES = recursiveFlag
        Flags.VERBOSE = verboseFlag

        val classResolver = InputStreamResolver()

        sources.forEach { path ->
            classResolver.resolveStreamsFromFile(path.toFile())
        }
    }
}
