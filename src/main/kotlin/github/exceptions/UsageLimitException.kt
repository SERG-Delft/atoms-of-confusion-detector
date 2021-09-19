package github.exceptions

class UsageLimitException :
    Throwable("Github API usage limits exceeded. Authenticate the request or change your IP address")
