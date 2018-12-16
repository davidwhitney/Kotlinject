package com.electrichead.kotlinject.registration.conditionalbinding

import com.electrichead.kotlinject.activation.ActivationContext

interface IBindingCondition {
    fun matches(context : ActivationContext) : Boolean
}

