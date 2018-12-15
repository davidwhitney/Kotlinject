package electrichead.kotlinject.activation

import electrichead.kotlinject.registration.Binding

interface IActivateTypes {
    fun create(binding: Binding): Any
}