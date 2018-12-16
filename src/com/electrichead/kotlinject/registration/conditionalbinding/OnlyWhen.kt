package com.electrichead.kotlinject.registration.conditionalbinding

import com.electrichead.kotlinject.activation.ActivationContext

class OnlyWhen(filter: ((ctx: ActivationContext) -> Boolean)) : IBindingCondition {
    val filter = filter

    override fun matches(context : ActivationContext): Boolean {
        return filter(context)
    }
}