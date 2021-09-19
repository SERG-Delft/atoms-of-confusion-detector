package github.exceptions

class InvalidPrUrlException(url: String) :
    Throwable("$url is not a valid pull request url")
