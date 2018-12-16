package electrichead.kotlinject.activation

import electrichead.kotlinject.registration.Binding

interface IActivateTypes {
    fun create(
        bindings: List<Binding>,
        activationContext: ActivationContext
    ): Any
}