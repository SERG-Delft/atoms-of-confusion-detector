package input

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import github.GithubUtil
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
 * Main CLI class
 */
class Tool : CliktCommand(help = "Analyze Java source code for the presence of atoms of confusion") {
    override fun run() {
        // do nothing
    }
}

abstract class AtomsCommand(help: String) : CliktCommand(help = help) {

    /**
     * Create a listener with all detectors registered
     *
     * @param confusionGraph the confusionGraph to write to
     * @return the listener
     */
    fun setUpListener(confusionGraph: ConfusionGraph): AtomsListener {

        val listener = AtomsListener()

        // register all detectors
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

        return listener
    }

    /**
     * Run the listener on the provided list of files. This method will read
     * CLI options and print/log accordingly
     *
     * @param files the files to run on
     * @param listener the listener
     * @param logName the name of the logfile
     */
    @Suppress("TooGenericExceptionCaught")
    fun runListener(files: List<ParsedFile>, listener: AtomsListener, logName: String) {

        var filesAnalyzed = 0
        val log = StringBuilder("==================Log=====================\n")

        // for each parsed file
        for (file in files) {

            try {
                listener.setFile(file)
                if (Settings.VERBOSE) println("analyzing ${file.name}...")
                ParseTreeWalker().walk(listener, file.parser.compilationUnit())
                filesAnalyzed++
            } catch (e: Exception) {
                // this catches a generic exception to account for anything that could go wrong
                log.append("Failed to analyse file: ${file.name} due to: \n ${e.message}\n")
            }
        }

        // write the log to a file
        if (Settings.LOG) {
            log.append("Successfully analysed $filesAnalyzed/${files.size} files\n")
            val logFile = File(logName)
            logFile.createNewFile()
            logFile.writeText(log.toString())
        }
    }
}

/**
 * CLI for the files subcommand
 */
class FilesCommand : AtomsCommand("Analyze the provided files for atoms of confusion") {

    private val recursiveFlag by option(
        "-r", "--recursive", "-R",
        help = "This flag tells the tool to recursively search any input directory for Java files"
    ).flag(default = Settings.RECURSIVELY_SEARCH_DIRECTORIES)

    private val verboseFlag by option(
        "-v", "--verbose", "-V",
        help = "This flag tells the tool to print the results of its analysis on the console"
    ).flag(default = Settings.VERBOSE)

    private val logFlag by option(
        "-l", "--log", "-L",
        help = "This flag tells the tool to log the progress of the analysis to a file"
    ).flag(default = Settings.LOG)

    // the file paths to be read
    private val inputFiles: List<Path> by argument()
        .path(mustExist = true, mustBeReadable = true)
        .multiple(required = true)

    override fun run() {

        // save cli settings
        Settings.RECURSIVELY_SEARCH_DIRECTORIES = recursiveFlag
        Settings.VERBOSE = verboseFlag
        Settings.LOG = logFlag

        val fileResolver = InputParser()

        inputFiles.forEach { path ->
            fileResolver.resolveFile(path.toFile())
        }

        val confusionGraph = ConfusionGraph(fileResolver.files.map { it.name })
        val listener = setUpListener(confusionGraph)

        // run the listener on the resolved files
        runListener(fileResolver.files, listener, "atomsLog.txt")

        // output the detected atoms
        CsvWriter.outputData(confusionGraph, "detectedAtoms.csv")
    }
}

/**
 * CLI for the pull request subcommand
 */
class PRCommand : AtomsCommand("Analyze the provided github pull request for atoms of confusion") {

    private val verboseFlag by option(
        "-v", "--verbose", "-V",
        help = "This flag tells the tool to print the results of its analysis on the console"
    ).flag(default = Settings.VERBOSE)

    private val logFlag by option(
        "-l", "--log", "-L",
        help = "This flag tells the tool to log the progress of the analysis to a file"
    ).flag(default = Settings.LOG)

    private val url: String by argument(help = "The github pr URL")

    override fun run() {

        // set flags
        Settings.VERBOSE = verboseFlag
        Settings.LOG = logFlag

        // extract data from the pull request url
        val pr = GithubUtil.getPullRequestInfo(url)

        // get changed files
        val javaFiles = GithubUtil.getChangedJavaFiles(pr.patch)

        // get the source and target files
        val sourceFiles = mutableListOf<ParsedFile>()
        val targetFiles = mutableListOf<ParsedFile>()

        // download all of the target files
        for (filePath in javaFiles) {
            val downloadedFile = GithubUtil.downloadFile(
                pr.sourceBranch.repo.user,
                pr.sourceBranch.repo.name,
                pr.sourceBranch.branch,
                filePath
            )
            if (downloadedFile != null) sourceFiles.add(downloadedFile)
        }

        // download all of the source files
        for (filePath in javaFiles) {
            val downloadedFile = GithubUtil.downloadFile(
                pr.targetBranch.repo.user,
                pr.targetBranch.repo.name,
                pr.targetBranch.branch,
                filePath
            )
            if (downloadedFile != null) targetFiles.add(downloadedFile)
        }

        val sourceGraph = ConfusionGraph(sourceFiles.map { it.name })
        val sourceListener = setUpListener(sourceGraph)

        println("analyzing source files...(${sourceFiles.size})")
        runListener(sourceFiles, sourceListener, "sourceLog.txt")
        CsvWriter.outputData(sourceGraph, "sourceResults.csv")

        val targetGraph = ConfusionGraph(sourceFiles.map { it.name })
        val targetListener = setUpListener(sourceGraph)

        println("analyzing target files...(${targetFiles.size})")
        runListener(targetFiles, targetListener, "targetLog.txt")
        CsvWriter.outputData(targetGraph, "targetResults.csv")
    }
}
