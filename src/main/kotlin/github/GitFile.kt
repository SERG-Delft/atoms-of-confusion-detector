package github

import org.antlr.v4.runtime.CharStream

data class GitFile(val path: String, val contents: CharStream)
