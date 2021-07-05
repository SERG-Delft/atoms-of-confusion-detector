package input

/**
 * This is a singleton class containing the values for the different flags of the tool.
 * The hardcoded values in this file act as the default values. The MainCommand class
 * of the same package is responsible for setting the flags based on the user input.
 * The flags are stored here so that they can be accessed in different parts of the
 * analysis pipeline. The class is a singleton so it provides one - uniform view of
 * the user input.
 */
object Flags {
    // controls if source directories are recursively searched for Java source code
    var RECURSIVELY_SEARCH_DIRECTORIES = false
    // controls if the tool outputs the results to the console
    var VERBOSE = false
}
