package output.writers

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
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
        csvWriter().writeAll(rows, "$filename.csv")
    }
}
