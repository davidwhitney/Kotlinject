package electrichead.kotlinject.registration.conditionalbinding

import electrichead.kotlinject.activation.ActivationContext

class OnlyWhen(filter: ((ctx: ActivationContext) -> Boolean)) : IBindingCondition {
    val filter = filter

    override fun matches(context : ActivationContext): Boolean {
        return filter(context)
    }
}