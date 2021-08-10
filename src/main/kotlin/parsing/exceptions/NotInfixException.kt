package parsing.exceptions

class NotInfixException(text: String) : Throwable("\"$text\" is not a valid infix expression")
