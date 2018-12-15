package Activation

import Registration.Binding

interface ICreateTypes {
    fun create(binding: Binding): Any
}