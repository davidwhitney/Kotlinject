package Activation

import java.lang.Exception

class MissingBindingException : Exception {
    constructor(details : String) : super(details)
}