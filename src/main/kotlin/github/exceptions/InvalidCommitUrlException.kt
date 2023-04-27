package github.exceptions

class InvalidCommitUrlException(url: String) :
    Throwable("$url is not a valid commit url")
