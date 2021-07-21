package parsing

import JavaLexer
import JavaParser
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream

data class ParsedFile(val stream: CharStream) {
    val lex = JavaLexer(stream)
    val tokens = CommonTokenStream(lex)
    val parser = JavaParser(tokens)
}
