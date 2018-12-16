package com.electrichead.kotlinject.registration.conditionalbinding

import com.electrichead.kotlinject.activation.ActivationContext

class AlwaysMatches : IBindingCondition {
    override fun matches(context : ActivationContext) : Boolean {
        return true
    }
}