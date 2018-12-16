package com.electrichead.kotlinject.activation

import com.electrichead.kotlinject.registration.Binding

interface IActivateTypes {
    fun create(
        bindings: List<Binding>,
        activationContext: ActivationContext
    ): Any
}