package github.exceptions

class NonexistentPRException(url: String) :
    Throwable("$url is not a valid pull request url (check if it is a public repo)")
