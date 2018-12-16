package electrichead.kotlinject.registration.conditionalbinding

import electrichead.kotlinject.activation.ActivationContext
import kotlin.reflect.KClass

class BindingConditions {
    fun alwaysMatches () : IBindingCondition {
        return AlwaysMatches()
    }

    fun whenInjectedInto(target: KClass<*>): IBindingCondition {
        return WhenInjectedInto(target)
    }

    fun onlyWhen(filter: ((ctx: ActivationContext) -> Boolean)): IBindingCondition {
        return OnlyWhen(filter)
    }
}

