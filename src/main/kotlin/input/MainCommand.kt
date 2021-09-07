package input

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import org.antlr.v4.runtime.tree.ParseTreeWalker
import output.graph.ConfusionGraph
import output.writers.CsvWriter
import parsing.AtomsListener
import parsing.ParsedFile
import parsing.detectors.ChangeOfLiteralEncodingDetector
import parsing.detectors.ConditionalOperatorDetector
import parsing.detectors.ConstantVariableDetector
import parsing.detectors.IndentationDetector
import parsing.detectors.InfixPrecedenceDetector
import parsing.detectors.LogicAsControlFlowDetector
import parsing.detectors.OmittedCurlyBracesDetector
import parsing.detectors.PostIncrementDecrementDetector
import parsing.detectors.PreIncrementDecrementDetector
import parsing.detectors.RemoveIndentationDetector
import parsing.detectors.TypeConversionDetector
import java.io.File
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

    @Suppress("TooGenericExceptionCaught")
    override fun run() {
        Settings.RECURSIVELY_SEARCH_DIRECTORIES = recursiveFlag
        Settings.VERBOSE = verboseFlag

        val classResolver = InputStreamResolver()

        sources.forEach { path ->
            classResolver.resolveStreamsFromFile(path.toFile())
        }

        val confusionGraph = ConfusionGraph(sources.map { it.toString() })
        val listener = AtomsListener()

        listener.registerDetector(LogicAsControlFlowDetector(listener, confusionGraph))
        listener.registerDetector(InfixPrecedenceDetector(listener, confusionGraph))
        listener.registerDetector(ConditionalOperatorDetector(listener, confusionGraph))
        listener.registerDetector(PostIncrementDecrementDetector(listener, confusionGraph))
        listener.registerDetector(PreIncrementDecrementDetector(listener, confusionGraph))
        listener.registerDetector(ChangeOfLiteralEncodingDetector(listener, confusionGraph))
        listener.registerDetector(OmittedCurlyBracesDetector(listener, confusionGraph))
        listener.registerDetector(RemoveIndentationDetector(listener, confusionGraph))
        listener.registerDetector(IndentationDetector(listener, confusionGraph))
        listener.registerDetector(TypeConversionDetector(listener, confusionGraph))
        listener.registerDetector(ConstantVariableDetector(listener, confusionGraph))

        // for each input stream get its parser
        val parsers = classResolver.streams.map { ParsedFile(it) }
        var filesAnalyzed = 0
        val errorString = StringBuilder()
        errorString.append("==================Log=====================\n")
        parsers.forEach {
            try {
                listener.setFile(it)
                if (verboseFlag) println("analyzing ${it.stream.sourceName}...")
                ParseTreeWalker().walk(listener, it.parser.compilationUnit())
                filesAnalyzed++
            } catch (e: Exception) {
                // this catches a generic exception to account for anything that could go wrong
                // when the tool is in verbose mode the exceptions are logged in a file that
                // is created in the end of the analysis
                errorString.append("Failed to analyse file: ${it.stream.sourceName} due to: \n ${e.message}\n")
            }
            CsvWriter.outputData(confusionGraph)
        }
        if (verboseFlag) {
            errorString.append("Successfully analysed $filesAnalyzed/${parsers.size} files\n")
            val logFile = File("AtomsAnalysisLog.txt")
            logFile.createNewFile()
            logFile.writeText(errorString.toString())
        }
    }
}
