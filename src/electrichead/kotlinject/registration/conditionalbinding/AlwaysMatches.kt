package electrichead.kotlinject.registration.conditionalbinding

import electrichead.kotlinject.activation.ActivationContext

class AlwaysMatches : IBindingCondition {
    override fun matches(context : ActivationContext) : Boolean {
        return true
    }
}