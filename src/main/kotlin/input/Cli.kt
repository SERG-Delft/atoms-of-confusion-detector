@file:Suppress("MaximumLineLength")

package input

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import github.DiffParser
import github.GhCommitData
import github.GithubUtil
import github.PRDelta
import org.antlr.v4.runtime.tree.ParseTreeWalker
import output.graph.ConfusionGraph
import output.writers.CsvWriter
import parsing.AtomsListener
import parsing.ParsedFile
import parsing.detectors.ChangeOfLiteralEncodingDetector
import parsing.detectors.ConditionalOperatorDetector
import parsing.detectors.ConstantVariableDetector
import parsing.detectors.Detector
import parsing.detectors.IndentationDetector
import parsing.detectors.InfixPrecedenceDetector
import parsing.detectors.LogicAsControlFlowDetector
import parsing.detectors.OmittedCurlyBracesDetector
import parsing.detectors.PostIncrementDecrementDetector
import parsing.detectors.PreIncrementDecrementDetector
import parsing.detectors.RemoveIndentationDetector
import parsing.detectors.RepurposedVariablesDetector
import parsing.detectors.TypeConversionDetector
import java.io.File
import java.nio.file.Path

/**
 * Main CLI class
 */
class Tool : CliktCommand(help = "Analyze Java source code for the presence of atoms of confusion") {

    // the atoms to be disabled
    private val disabledAtoms: List<String> by option(
        "-d", "--disabled", help = "Space separated list of disabled atoms"
    ).multiple()

    override fun run() {

        // disable provided atoms
        disabledAtoms.forEach {
            Settings.enabledAtoms[it] = false
        }
    }
}

/**
 * A base class for subcommands, provides functionality shared between them
 */
abstract class AtomsCommand(help: String) : CliktCommand(help = help) {

    /**
     * Create a listener with all detectors registered
     *
     * @param confusionGraph the confusionGraph to write to
     * @return the listener
     */
    @Suppress("ComplexMethod", "MaxLineLength")
    fun setUpListener(confusionGraph: ConfusionGraph): AtomsListener {

        // create the listener
        val lsnr = AtomsListener()

        fun enabled(atom: String) = Settings.enabledAtoms[atom] == true

        fun register(detector: Detector) {
            lsnr.registerDetector(detector)
        }

        // register all enabled detectors
        if (enabled("INFIX_OPERATOR_PRECEDENCE")) register(InfixPrecedenceDetector(lsnr, confusionGraph))
        if (enabled("POST_INCREMENT_DECREMENT")) register(PostIncrementDecrementDetector(lsnr, confusionGraph))
        if (enabled("PRE_INCREMENT_DECREMENT")) register(PreIncrementDecrementDetector(lsnr, confusionGraph))
        if (enabled("CONSTANT_VARIABLES")) register(ConstantVariableDetector(lsnr, confusionGraph))
        if (enabled("REMOVE_INDENTATION")) register(RemoveIndentationDetector(lsnr, confusionGraph))
        if (enabled("CONDITIONAL_OPERATOR")) register(ConditionalOperatorDetector(lsnr, confusionGraph))
        // ARITHMETIC_AS_LOGIC (skipped)
        if (enabled("LOGIC_AS_CONTROL_FLOW")) register(LogicAsControlFlowDetector(lsnr, confusionGraph))
        if (enabled("REPURPOSED_VARIABLES")) register(RepurposedVariablesDetector(lsnr, confusionGraph))
        // DEAD_UNREACHABLE_REPEATED (skipped)
        if (enabled("CHANGE_OF_LITERAL_ENCODING")) register(ChangeOfLiteralEncodingDetector(lsnr, confusionGraph))
        if (enabled("OMITTED_CURLY_BRACES")) register(OmittedCurlyBracesDetector(lsnr, confusionGraph))
        if (enabled("TYPE_CONVERSION")) register(TypeConversionDetector(lsnr, confusionGraph))
        if (enabled("INDENTATION")) register(IndentationDetector(lsnr, confusionGraph))

        return lsnr
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
@Suppress("NestedBlockDepth")
class PRCommand : AtomsCommand("Analyze the provided github pull request for atoms of confusion") {

    private val verboseFlag by option(
        "-v", "--verbose", "-V",
        help = "This flag tells the tool to print the results of its analysis on the console"
    ).flag(default = Settings.VERBOSE)

    private val logFlag by option(
        "-l", "--log", "-L",
        help = "This flag tells the tool to log the progress of the analysis to a file"
    ).flag(default = Settings.LOG)

    private val downloadFlag by option(
        "-dl", "--download", "-DL",
        help = "Download all of the affected files in the pull request both before and after the merge"
    ).flag(default = Settings.LOG)

    private val url: String by argument(help = "The github pr URL")

    // run the detector on the to/from files
    private fun runDetector(
        fileNames: List<String>,
        commit: GhCommitData,
        resultsDir: String,
        toOrFrom: String,
    ): ConfusionGraph {

        // get the files
        val files = mutableListOf<ParsedFile>()

        // download all of the from files
        fileNames.forEach { filePath ->
            val downloadedFile = GithubUtil.downloadFile(commit, filePath)
            if (downloadedFile != null) {
                if (Settings.DOWNLOAD) {
                    File("$resultsDir/${toOrFrom}Files").mkdir()
                    File("$resultsDir/${toOrFrom}Files/${downloadedFile.name.replace("/", "-")}")
                        .writeText(downloadedFile.stream.toString())
                }
                files.add(downloadedFile)
            }
        }

        // run detector on from files
        val confusionGraph = ConfusionGraph(fileNames)
        val listener = setUpListener(confusionGraph)
        runListener(files, listener, "${toOrFrom}Log.txt")

        return confusionGraph
    }

    override fun run() {

        // set flags
        Settings.VERBOSE = verboseFlag
        Settings.LOG = logFlag
        Settings.DOWNLOAD = downloadFlag

        // create results directory
        val resultsDir = "./atoms-of-confusion-results"
        File(resultsDir).mkdir()

        // extract data from the pull request url
        val pr = GithubUtil.getPullRequestInfo(url)

        // read the diff file
        val parsedDiff = DiffParser(pr.diff)

        // get the filenames of the .java files that were modified in the pr
        val fromFiles = parsedDiff.fromFileNames.filter { it.endsWith(".java") }
        val toFiles = parsedDiff.toFileNames.filter { it.endsWith(".java") }

        val fromGraph = runDetector(fromFiles, pr.fromCommit, resultsDir, "from")
        val toGraph = runDetector(toFiles, pr.toCommit, resultsDir, "to")

        // output the to and from atoms
        CsvWriter.outputData(fromGraph, "$resultsDir/fromAtoms.csv")
        CsvWriter.outputData(toGraph, "$resultsDir/toAtoms.csv")

        // compute the pr delta
        val delta = PRDelta(toGraph, fromGraph, toFiles, fromFiles, parsedDiff)

        // output the pr delta
        CsvWriter.outputData(delta, "${pr.repo.user}-${pr.repo.name}-${pr.number}", resultsDir)
    }
}
