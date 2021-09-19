import com.github.ajalt.clikt.core.subcommands
import input.FilesCommand
import input.PRCommand
import input.Tool

fun main(args: Array<String>) = Tool().subcommands(FilesCommand(), PRCommand()).main(args)
