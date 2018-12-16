package electrichead.kotlinject.registration.conditionalbinding

import kotlin.reflect.KClass

class BindingConditions {
    fun alwaysMatches () : IBindingCondition {
        return AlwaysMatches()
    }

    fun whenInjectedInto(target: KClass<*>): IBindingCondition {
        return WhenInjectedInto(target)
    }
}

