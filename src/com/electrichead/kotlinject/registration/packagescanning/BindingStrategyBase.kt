package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.Lifecycle
import com.electrichead.kotlinject.registration.conditionalbinding.BindingConditions
import com.electrichead.kotlinject.registration.conditionalbinding.IBindingCondition

abstract class BindingStrategyBase(
    val condition: ((op: BindingConditions) -> IBindingCondition)? = null,
    lifecycle: Lifecycle? = null
) : IBindingStrategy {
    val lifeCycle : Lifecycle? = lifecycle
}