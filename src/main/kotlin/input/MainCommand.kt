package input

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple

/**
 * This is the class representing the command that runs when the tool is invoked.
 * It implements the parsing of the CLI arguments and options.
 */
class MainCommand : CliktCommand(help = "Analyze the provided files for atoms of confusion") {

    val sources: List<String> by argument().multiple(true)

    override fun run() {
        sources.forEach {
            echo(it)
        }
    }
}
