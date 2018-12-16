package com.electrichead.kotlinject

import java.lang.Exception

class CircularDependencyException(ex : StackOverflowError)
    : Exception("You attempted to create a type with a circular dependency. Cannot activate. Have you tried creating a factory for this type?", ex)
