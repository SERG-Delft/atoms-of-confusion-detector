package input

/**
 * In here we define the different locations from where source code can be retrieved.
 * Each data class contains a 'location' field which is the address of the file where the source
 * code resides. For example for the GitHubRawSource the location corresponds to the URL
 * of a raw file on github (e.g. https://raw.githubusercontent.com/JetBrains/kotlin/master/ReadMe.md).
 */

sealed class InputSource(open val location: String)
data class GitHubRawSource(override val location: String) : InputSource(location)
data class GitHubPullRequestSource(override val location: String) : InputSource(location)
data class LocalFileSource(override val location: String) : InputSource(location)
data class LocalDirectorySource(override val location: String) : InputSource(location)
