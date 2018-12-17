package com.electrichead.kotlinject.registration.conditionalbinding

import com.electrichead.kotlinject.activation.ActivationContext
import kotlin.reflect.KClass

class BindingConditions {
    fun alwaysMatches () : IBindingCondition {
        return AlwaysMatches()
    }

    fun whenInjectedInto(target: java.lang.Class<*>): IBindingCondition {
        return whenInjectedInto(target.kotlin)
    }

    fun whenInjectedInto(target: KClass<*>): IBindingCondition {
        return WhenInjectedInto(target)
    }

    fun onlyWhen(filter: ((ctx: ActivationContext) -> Boolean)): IBindingCondition {
        return OnlyWhen(filter)
    }
}

