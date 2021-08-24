package input

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import github.GitFile
import github.GithubUtil
import org.antlr.v4.runtime.tree.ParseTreeWalker
import output.graph.ConfusionGraph
import output.writers.CsvWriter
import parsing.AtomsListener
import parsing.ParsedFile
import parsing.detectors.ConditionalOperatorDetector
import parsing.detectors.InfixPrecedenceDetector
import parsing.detectors.LogicAsControlFlowDetector
import parsing.detectors.PostIncrementDecrementDetector
import parsing.detectors.PreIncrementDecrementDetector
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

    override fun run() {

        // extract data from the pull request url
        val pr = GithubUtil.getPullRequestInfo(url)

        // get changed files
        val javaFiles = GithubUtil.getChangedJavaFiles(pr.patch)

        // get the source and target files
        val sourceFiles = mutableListOf<GitFile>()
        val targetFiles = mutableListOf<GitFile>()

        for (filePath in javaFiles) {
            val downloadedFile = GithubUtil.downloadFile(
                pr.targetBranch.repo.user,
                pr.targetBranch.repo.name,
                pr.targetBranch.branch,
                filePath
            )
            if (downloadedFile != null) targetFiles.add(downloadedFile)
        }

        for (filePath in javaFiles) {
            val downloadedFile = GithubUtil.downloadFile(
                pr.sourceBranch.repo.user,
                pr.sourceBranch.repo.name,
                pr.sourceBranch.branch,
                filePath
            )
            if (downloadedFile != null) sourceFiles.add(downloadedFile)
        }

        val confusionGraph = ConfusionGraph(sourceFiles.map { it.path })
        val listener = AtomsListener()

        listener.registerDetector(LogicAsControlFlowDetector(listener, confusionGraph))
        listener.registerDetector(InfixPrecedenceDetector(listener, confusionGraph))
        listener.registerDetector(ConditionalOperatorDetector(listener, confusionGraph))
        listener.registerDetector(PostIncrementDecrementDetector(listener, confusionGraph))
        listener.registerDetector(PreIncrementDecrementDetector(listener, confusionGraph))

        println("analyzing source files...")

        sourceFiles.forEach {
            val file = ParsedFile(it.contents)
            listener.fileName = it.path
            val tree = file.parser.compilationUnit()
            val walker = ParseTreeWalker()
            walker.walk(listener, tree)
        }

        CsvWriter.outputData(confusionGraph, "source")

        println("analyzing target files...")

        val confusionGraph2 = ConfusionGraph(sourceFiles.map { it.path })
        val listener2 = AtomsListener()

        listener2.registerDetector(LogicAsControlFlowDetector(listener, confusionGraph2))
        listener2.registerDetector(InfixPrecedenceDetector(listener, confusionGraph2))
        listener2.registerDetector(ConditionalOperatorDetector(listener, confusionGraph2))
        listener2.registerDetector(PostIncrementDecrementDetector(listener, confusionGraph2))
        listener2.registerDetector(PreIncrementDecrementDetector(listener, confusionGraph2))

        targetFiles.forEach {
            val file = ParsedFile(it.contents)
            listener2.fileName = it.path
            val tree = file.parser.compilationUnit()
            val walker = ParseTreeWalker()
            walker.walk(listener2, tree)
        }

        CsvWriter.outputData(confusionGraph2, "target")
    }
}
