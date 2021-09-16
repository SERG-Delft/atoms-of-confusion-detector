package output.writers

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import github.PRDelta
import input.Settings
import output.graph.ConfusionGraph

object CsvWriter {

    /**
     * Outputs the given graph as a csv file.
     *
     * @param graph the graph to output.
     */
    fun outputData(graph: ConfusionGraph) {
        val rows = graph.getAllAtomAppearances()
        csvWriter().writeAll(rows, "${Settings.OUTPUT}.csv")
    }

    /**
     * Outputs the given graph as a csv file.
     *
     * @param graph the graph to output.
     * @param filename the file name of the output file
     */
    fun outputData(graph: ConfusionGraph, filename: String) {
        val rows = graph.getAllAtomAppearances()
        csvWriter().writeAll(rows, filename)
    }

    /**
     * Outputs the PR delta to csv files.
     *
     * @param delta the PRDelta
     * @param prName the name of the pull request
     */
    fun outputData(
        delta: PRDelta,
        prName: String
    ) {

        csvWriter().writeAll(delta.getAddedAtoms(), "addedAtoms-$prName.csv")
        csvWriter().writeAll(delta.getRemovedAtoms(), "removedAtoms-$prName.csv")
        csvWriter().writeAll(delta.getRemainingAtoms(), "remainingAtoms-$prName.csv")
    }
}
