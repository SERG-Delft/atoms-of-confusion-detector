package github

import output.graph.ConfusionGraph

class PRDelta(
    toGraph: ConfusionGraph,
    fromGraph: ConfusionGraph,
    toFiles: List<String>,
    fromFiles: List<String>,
    parsedDiff: DiffParser
) {

    private val addedAtoms = mutableListOf<List<String>>()
    private val removedAtoms = mutableListOf<List<String>>()
    private val remainingAtoms = mutableListOf<List<String>>()

    private fun addAtomInstance(list: MutableList<List<String>>, atomName: String, fileName: String, lineNum: Int) {
        list.add(listOf(atomName, fileName, lineNum.toString()))
    }

    init {

        // for each toAtom, check if it is newly added or remaining from before
        for (file in toFiles) {
            val atomsInFile = toGraph.findAtomsInSource(file)
            for (atom in atomsInFile) {
                for (line in atom.lines) {
                    if (parsedDiff.addedLinesForFile(file).contains(line)) {
                        addAtomInstance(addedAtoms, atom.nameOfAtom, file, line)
                    } else {
                        addAtomInstance(remainingAtoms, atom.nameOfAtom, file, line)
                    }
                }
            }
        }

        // for each fromAtom, check if it is removed
        for (file in fromFiles) {
            val atomsInFile = fromGraph.findAtomsInSource(file)
            for (atom in atomsInFile) {
                for (line in atom.lines) {
                    if (parsedDiff.removedLinesForFile(file).contains(line)) {
                        addAtomInstance(removedAtoms, atom.nameOfAtom, file, line)
                    }
                }
            }
        }
    }

    fun getAddedAtoms() = addedAtoms
    fun getRemovedAtoms() = removedAtoms
    fun getRemainingAtoms() = remainingAtoms
}
