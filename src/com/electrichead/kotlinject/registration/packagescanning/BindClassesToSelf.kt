package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.Lifecycle
import com.electrichead.kotlinject.registration.TypeRegistry
import com.electrichead.kotlinject.registration.conditionalbinding.BindingConditions
import com.electrichead.kotlinject.registration.conditionalbinding.IBindingCondition
import kotlin.reflect.KClass

class BindClassesToSelf(condition: ((op: BindingConditions) -> IBindingCondition)?, lifecycle: Lifecycle?) :
    BindingStrategyBase(condition, lifecycle) {
    override fun bind(typeRegistry: TypeRegistry, classes: List<KClass<*>>) {
        classes
            .filter { c -> !c.java.isInterface }
            .forEach { c -> typeRegistry.bind(c, c, condition, lifeCycle) }
    }
}

