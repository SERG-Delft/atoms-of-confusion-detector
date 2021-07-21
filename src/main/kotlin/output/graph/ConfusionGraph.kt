package output.graph

import output.Atom
import output.exceptions.SourceDoesNotExistException
import output.graph.nodes.AtomNode
import output.graph.nodes.SourceNode
import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

class ConfusionGraph(val sources: List<String>) {

    val atomNodes = mutableMapOf<String, AtomNode>()
    val sourceNodes = mutableMapOf<String, SourceNode>()

    // initialize both atomNodes and sourceNodes
    init {
        Atom.values().forEach {
            atomNodes[it.name] = AtomNode(it.name)
        }
        sources.forEach {
            sourceNodes[it] = SourceNode(it)
        }
    }

    /**
     * Adds an appearance of the given atom.
     *
     * @param atomName the name of the atom.
     * @param sourceName the name of the source file in which the atom appears.
     * @param lines the set of lines in which the atom appears in.
     * @throws IllegalArgumentException iff the atom name is invalid.
     */
    @Throws(IllegalArgumentException::class)
    fun addAppearancesOfAtom(atomName: String, sourceName: String, lines: MutableSet<Int>) {
        try {
            Atom.valueOf(atomName)
        } catch (e: IllegalArgumentException) {
            println(e.stackTrace)
            throw IllegalArgumentException("$atomName is not a valid atom")
        }
        val atomNode = atomNodes[atomName]!!
        if (!sourceNodes.containsKey(sourceName)) {
            sourceNodes[sourceName] = SourceNode(sourceName)
        }
        val sourceNode = sourceNodes[sourceName]!!
        if (sourceNode.hasNeighbour(atomNode)) {
            val edge = sourceNode.getEdgeToNeighbour(atomNode)
            sourceNode.addNeighbour(atomNode, Edge(edge.lines.union(lines).toMutableSet(), atomNode, sourceNode))
        } else {
            val edge = Edge(lines, atomNode, sourceNode)
            atomNode.addNeighbour(sourceNode, edge)
        }
    }

    /**
     * Adds an appearance of the given atom.
     *
     * @param atomName the atom.
     * @param sourceName the name of the source file in which the atom appears.
     * @param lines the set of lines in which the atom appears in.
     * @throws IllegalArgumentException iff the atom name is invalid.
     */
    fun addAppearancesOfAtom(atomName: Atom, sourceName: String, lines: MutableSet<Int>) {
        addAppearancesOfAtom(atomName.name, sourceName, lines)
    }

    /**
     * Counts the total lines in which an atom appears.
     *
     * @param atom the Atom to find.
     * @return the count of lines.
     */
    fun countLineAppearancesOfAtom(atom: Atom): Int {
        val node = atomNodes[atom.name]!!
        return node.edgeMap.values.fold(0) { sum, edge -> sum + edge.lines.size }
    }

    /**
     * Counts the number of files in which an atom appears.
     *
     * @param atom the Atom to find.
     * @return the count of files in which the atom appears.
     */
    fun countFileAppearancesOfAtom(atom: Atom): Int {
        return atomNodes[atom.name]!!.edgeMap.size
    }

    /**
     * Finds all the lines on all the files an atom appears.
     *
     * @param atom the Atom to find.
     * @return a list of Filename, Lines tuples.
     */
    fun findAppearancesOfAtom(atom: Atom): List<AtomInSourceAppearance> {
        val neighbours = atomNodes[atom.name]!!.edgeMap
        return neighbours.keys.map { it -> AtomInSourceAppearance(it.name, atom.name, neighbours[it]!!.lines) }
    }

    /**
     * Finds all of the atoms in a given source file.
     *
     * @param source the source name.
     * @throws SourceDoesNotExistException iff the source file given does not exist.
     * @return a list of Atom, Lines tuples.
     */
    fun findAtomsInSource(source: String): List<AtomInSourceAppearance> {
        if (!sourceNodes.contains(source)) throw SourceDoesNotExistException(source)
        val neighbours = sourceNodes[source]!!.edgeMap
        return neighbours.keys.map { it -> AtomInSourceAppearance(source, it.name, neighbours[it]!!.lines) }
    }

    /**
     * Returns all appearances of all atoms
     *
     * @returns a list of lists representing all appearances.
     */
    fun getAllAtomAppearances(): List<List<Any>> {
        return Atom.values().flatMap { it -> findAppearancesOfAtom(it).map { x -> x.toList() } }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConfusionGraph

        if (sources != other.sources) return false
        if (atomNodes != other.atomNodes) return false
        if (sourceNodes != other.sourceNodes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sources.hashCode()
        result = 31 * result + atomNodes.hashCode()
        result = 31 * result + sourceNodes.hashCode()
        return result
    }

    override fun toString(): String {
        return "ConfusionGraph(sources=$sources, atomNodes=$atomNodes, sourceNodes=$sourceNodes)"
    }
}

data class AtomInSourceAppearance(val nameOfSource: String, val nameOfAtom: String, val lines: Set<Int>) {
    fun toList(): List<Any> {
        return listOf(nameOfAtom, nameOfSource, lines)
    }
}
