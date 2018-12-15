package electrichead.kotlinject.activation

import electrichead.kotlinject.registration.Binding

interface ICreateTypes {
    fun create(binding: Binding): Any
}