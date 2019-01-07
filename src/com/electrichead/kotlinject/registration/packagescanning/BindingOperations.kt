package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.Lifecycle
import com.electrichead.kotlinject.registration.conditionalbinding.BindingConditions
import com.electrichead.kotlinject.registration.conditionalbinding.IBindingCondition

class BindingOperations {
    @JvmOverloads
    fun bindAllInterfaces(
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): IBindingStrategy {
        return BindAllInterfaces(condition, lifecycle)
    }

    @JvmOverloads
    fun bindClassesToSelf(
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): IBindingStrategy {
        return BindClassesToSelf(condition, lifecycle)
    }

    @JvmOverloads
    fun bindClassesAndInterfaces(
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): IBindingStrategy {
        return BindClassesAndInterfaces(condition, lifecycle)
    }
}