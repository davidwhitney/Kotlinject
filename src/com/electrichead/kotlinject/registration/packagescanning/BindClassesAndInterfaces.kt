package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.Lifecycle
import com.electrichead.kotlinject.registration.TypeRegistry
import com.electrichead.kotlinject.registration.conditionalbinding.BindingConditions
import com.electrichead.kotlinject.registration.conditionalbinding.IBindingCondition
import kotlin.reflect.KClass

class BindClassesAndInterfaces(condition: ((op: BindingConditions) -> IBindingCondition)?, lifecycle: Lifecycle?) :
    BindingStrategyBase(condition, lifecycle) {
    private var _classes: BindClassesToSelf = BindClassesToSelf(condition, lifecycle)
    private var _interfaces: BindAllInterfaces = BindAllInterfaces(condition, lifecycle)

    override fun bind(typeRegistry: TypeRegistry, classes: List<KClass<*>>) {
        _classes.bind(typeRegistry, classes)
        _interfaces.bind(typeRegistry, classes)
    }
}