package input

/**
 * This is a singleton class containing the values for the different flags and options of the tool.
 * The hardcoded values in this file act as the default values. The MainCommand class
 * of the same package is responsible for setting the flags based on the user input.
 * The flags/options are stored here so that they can be accessed in different parts of the
 * analysis pipeline. The class is a singleton so it provides one - uniform view of
 * the user input.
 */
object Settings {
    // controls if source directories are recursively searched for Java source code
    var RECURSIVELY_SEARCH_DIRECTORIES = false
    // controls if the tool outputs the results to the console
    var VERBOSE = false
    // defines the name of the output
    var OUTPUT = "atoms-of-confusion-analysis"
    // controls if the tool logs errors
    var LOG = false
    // controls if the tool downloads files
    var DOWNLOAD = false

    // the optional API token
    var TOKEN: String? = null

    val enabledAtoms = mutableMapOf(
        "INFIX_OPERATOR_PRECEDENCE" to true,
        "PRE_INCREMENT_DECREMENT" to true,
        "CONSTANT_VARIABLES" to true,
        "REMOVE_INDENTATION" to true,
        "CONDITIONAL_OPERATOR" to true,
        "ARITHMETIC_AS_LOGIC" to true,
        "LOGIC_AS_CONTROL_FLOW" to true,
        "REPURPOSED_VARIABLES" to true,
        "DEAD_UNREACHABLE_REPEATED" to true,
        "CHANGE_OF_LITERAL_ENCODING" to true,
        "OMITTED_CURLY_BRACES" to true,
        "TYPE_CONVERSION" to true,
        "INDENTATION" to true
    )
}
