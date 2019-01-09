package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.Lifecycle
import com.electrichead.kotlinject.registration.TypeRegistry
import com.electrichead.kotlinject.registration.conditionalbinding.BindingConditions
import com.electrichead.kotlinject.registration.conditionalbinding.IBindingCondition
import kotlin.reflect.KClass

class BindAllInterfaces(condition: ((op: BindingConditions) -> IBindingCondition)?, lifecycle: Lifecycle?) :
    BindingStrategyBase(condition, lifecycle) {
    override fun bind(typeRegistry: TypeRegistry, classes: List<KClass<*>>) {
        classes
            .filter { x -> !x.java.isInterface }
            .forEach { c -> c.java.interfaces.forEach { i -> typeRegistry.bind(i.kotlin, c, condition, lifeCycle) }}
    }
}