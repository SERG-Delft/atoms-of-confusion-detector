package output.exceptions

class SourceDoesNotExistException(private val sourceName: String) : Throwable("Source $sourceName does not exist")
