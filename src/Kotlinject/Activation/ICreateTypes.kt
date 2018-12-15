package Kotlinject.Activation

import Kotlinject.Registration.Binding

interface ICreateTypes {
    fun create(binding: Binding): Any
}