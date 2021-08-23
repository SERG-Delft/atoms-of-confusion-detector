package input

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.jsoup.Jsoup
import output.graph.ConfusionGraph
import output.writers.CsvWriter
import parsing.AtomsListener
import parsing.ParsedFile
import parsing.detectors.ConditionalOperatorDetector
import parsing.detectors.InfixPrecedenceDetector
import parsing.detectors.LogicAsControlFlowDetector
import parsing.detectors.PostIncrementDecrementDetector
import parsing.detectors.PreIncrementDecrementDetector
import java.net.URI
import java.nio.file.Path

/**
 * Main CLI class
 */
class Tool : CliktCommand(help = "Analyze Java source code for the presence of atoms of confusion") {
    override fun run() {
        // do nothing
    }
}

/**
 * CLI for the files subcommand
 */
class FilesCommand : CliktCommand(help = "Analyze the provided files for atoms of confusion") {

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
        val listener = AtomsListener()

        listener.registerDetector(LogicAsControlFlowDetector(listener, confusionGraph))
        listener.registerDetector(InfixPrecedenceDetector(listener, confusionGraph))
        listener.registerDetector(ConditionalOperatorDetector(listener, confusionGraph))
        listener.registerDetector(PostIncrementDecrementDetector(listener, confusionGraph))
        listener.registerDetector(PreIncrementDecrementDetector(listener, confusionGraph))

        // for each input stream get its parser
        val parsers = classResolver.streams.map { ParsedFile(it) }
        parsers.forEach {
            listener.fileName = it.stream.sourceName
            val tree = it.parser.compilationUnit()
            val walker = ParseTreeWalker()
            walker.walk(listener, tree)
        }

        CsvWriter.outputData(confusionGraph)
    }
}

/**
 * CLI for the pull request subcommand
 */
class PRCommand : CliktCommand(help = "Analyze the provided github pull request for atoms of confusion") {

    private val url: String by argument(help = "The github pr URL")

    @SuppressWarnings("MagicNumber")
    override fun run() {

        val uri = URI(url)
        val path = uri.path.split("/")

        // val user = path[1]
        val repo = path[2]
        // val prNum = path[4]

        val doc = Jsoup.connect(url).get()
        val elements = doc.select(".commit-ref")
        val target = elements[0].text().split(":")
        val source = elements[1].text().split(":")

        val (targetRepoUsername, targetBranch) = target[0] to target[1]
        val (sourceRepoUsername, sourceBranch) = source[0] to source[1]

        val (request, response, result) = "$url.patch".httpGet().responseString()
        val patch = result.component1()!!

        val changedFiles = patch.split("\n")
            .filter { it.length >= 3 && it.slice(0 until 3) == "+++" } // get lines starting with +++
            .map { it.split(" ")[1] } // get the path, following the plus signs
            .map { it.slice(2 until it.length) } // remove the b/ from each path

        val sourceFiles = mutableListOf<CharStream>()
        val targetFiles = mutableListOf<CharStream>()

        for (file in changedFiles) {

            val url = "http://raw.githubusercontent.com/$targetRepoUsername/$repo/$targetBranch/$file"
            val (_, response, result) = url.httpGet().responseString()

            if (response.isSuccessful) {
                val fileText = result.component1()!!
                targetFiles.add(CharStreams.fromString(fileText))
            }
        }

        for (file in changedFiles) {

            val url = "http://raw.githubusercontent.com/$sourceRepoUsername/$repo/$sourceBranch/$file"
            val (_, response, result) = url.httpGet().responseString()

            if (response.isSuccessful) {
                val fileText = result.component1()!!
                sourceFiles.add(CharStreams.fromString(fileText))
            }
        }
    }
}
