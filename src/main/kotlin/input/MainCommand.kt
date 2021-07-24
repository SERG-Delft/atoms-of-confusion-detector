package input

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import output.graph.ConfusionGraph
import output.writers.CsvWriter
import parsing.AtomsVisitor
import parsing.ParsedFile
import parsing.detectors.ConditionalOperatorDetector
import parsing.detectors.LogicAsControlFlowDetector
import java.nio.file.Path

/**
 * This is the class representing the command that runs when the tool is invoked.
 * It implements the parsing of the CLI arguments and options.
 */
class MainCommand : CliktCommand(help = "Analyze the provided files for atoms of confusion") {

    private val recursiveFlag by option(
        "-r", "--recursive", "-R",
        help = "This flag tells the tool to recursively search any input directory for Java files"
    ).flag(default = Settings.RECURSIVELY_SEARCH_DIRECTORIES)
    private val verboseFlag by option(
        "-v", "--verbose", "-V",
        help = "This flag tells the tool to print the results of its analysis on the console"
    ).flag(default = Settings.VERBOSE)

    private val sources: List<Path> by argument()
        .path(mustExist = true, mustBeReadable = true)
        .multiple(required = true)

    override fun run() {
        Settings.RECURSIVELY_SEARCH_DIRECTORIES = recursiveFlag
        Settings.VERBOSE = verboseFlag

        val classResolver = InputStreamResolver()

        sources.forEach { path ->
            classResolver.resolveStreamsFromFile(path.toFile())
        }

        val confusionGraph = ConfusionGraph(sources.map { it.toString() })
        val visitor = AtomsVisitor()

        visitor.registerDetector(LogicAsControlFlowDetector(visitor, confusionGraph))
        visitor.registerDetector(ConditionalOperatorDetector(visitor, confusionGraph))

        // for each input stream get its parser
        val parsers = classResolver.streams.map { ParsedFile(it) }
        parsers.forEach {

            visitor.fileName = it.stream.sourceName

            val tree = it.parser.compilationUnit()
            tree.accept(visitor)
        }

        CsvWriter.outputData(confusionGraph)
    }
}
