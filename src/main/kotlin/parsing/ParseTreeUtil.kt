package parsing

import JavaParser
import org.antlr.v4.runtime.tree.ParseTree

object ParseTreeUtil {

    /**
     * Returns nodes index with in its parent's child array
     *
     * @param node a child node
     * @return the nodes index or null if it doesn't have a parent
     */
    fun getNodeIndex(node: ParseTree): Int? {

        if (node.parent == null) return null

        val parent = node.parent

        for (i in 0 until parent.childCount) {
            if (parent.getChild(i) === node) return i
        }

        return null
    }

    /**
     * Gets left sibling of a parse tree node.
     *
     * @param node a parse tree node
     * @return the left sibling of a node, or null if there is no sibling
     */
    fun getLeftSibling(node: ParseTree): ParseTree? {
        val index = getNodeIndex(node) ?: return null
        return node.parent.getChild(index - 1)
    }

    /**
     * Gets right sibling of a parse tree node.
     *
     * @param node a parse tree node
     * @return the right sibling of a node, or null if no sibling is found
     */
    fun getRightSibling(node: ParseTree): ParseTree? {
        val index = getNodeIndex(node)
        val parent = node.parent

        if (index == null || index >= parent.childCount - 1) return null

        return parent.getChild(index + 1)
    }

    /**
     * Returns true if stat is a block statement (that is, it is located in a block)
     *
     * @param stat a statement
     * @return true if this statement is a block statement
     */
    fun isBlockStatement(stat: JavaParser.StatementContext) = stat.parent is JavaParser.BlockStatementContext

    /**
     * Get block statement that precedes the provided statement
     *
     * @param stat the statement
     * @return the block statement that follows stat or null if no such statement exists
     */
    fun nextBlockStatement(stat: JavaParser.StatementContext): ParseTree? {
        val next = ParseTreeUtil.getRightSibling(stat.parent) ?: return null
        if (next.text == "}") return null
        return next
    }
}
