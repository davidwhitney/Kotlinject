package electrichead.kotlinject.registration.conditionalbinding

import electrichead.kotlinject.activation.ActivationContext

interface IBindingCondition {
    fun matches(context : ActivationContext) : Boolean
}

