package input

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option

/**
 * This is the class representing the command that runs when the tool is invoked.
 * It implements the parsing of the CLI arguments and options.
 */
class MainCommand : CliktCommand(help = "Analyze the provided files for atoms of confusion") {

    private val sources: List<String> by argument().multiple(true)
    private val recursiveFlag by option("-r").flag(default = Flags.RECURSIVELY_SEARCH_DIRECTORIES)
    private val verboseFlag by option("-v").flag(default = Flags.VERBOSE)

    override fun run() {
        Flags.RECURSIVELY_SEARCH_DIRECTORIES = recursiveFlag
        Flags.VERBOSE = verboseFlag
        sources.forEach {
            echo(it)
        }
    }
}
