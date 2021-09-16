package github

import output.graph.ConfusionGraph
import parsing.ParsedFile

class PRDelta(
    sourceGraph: ConfusionGraph,
    targetGraph: ConfusionGraph,
    sourceFiles: List<ParsedFile>,
    targetFiles: List<ParsedFile>,
    parsedDiff: DiffParser
) {

    private val addedAtoms = mutableListOf<List<String>>()
    private val removedAtoms = mutableListOf<List<String>>()
    private val remainingAtoms = mutableListOf<List<String>>()

    private fun addAtomInstance(list: MutableList<List<String>>, atomName: String, fileName: String, lineNum: Int) {
        list.add(listOf(atomName, fileName, lineNum.toString()))
    }

    init {

        // for each atom in the target, check if it is newly added or remaining from before
        for (file in targetFiles) {
            val atomsInFile = targetGraph.findAtomsInSource(file.name)
            for (atom in atomsInFile) {
                for (line in atom.lines) {
                    if (parsedDiff.addedLinesForFile(file.name).contains(line)) {
                        addAtomInstance(addedAtoms, atom.nameOfAtom, file.name, line)
                    } else {
                        addAtomInstance(remainingAtoms, atom.nameOfAtom, file.name, line)
                    }
                }
            }
        }

        // for each atom in the source, check if it is removed
        for (file in sourceFiles) {
            val atomsInFile = sourceGraph.findAtomsInSource(file.name)
            for (atom in atomsInFile) {
                for (line in atom.lines) {
                    if (parsedDiff.removedLinesForFile(file.name).contains(line)) {
                        addAtomInstance(removedAtoms, atom.nameOfAtom, file.name, line)
                    }
                }
            }
        }
    }

    fun getAddedAtoms() = addedAtoms
    fun getRemovedAtoms() = removedAtoms
    fun getRemainingAtoms() = remainingAtoms
}
