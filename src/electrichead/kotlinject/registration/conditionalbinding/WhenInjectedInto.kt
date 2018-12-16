package electrichead.kotlinject.registration.conditionalbinding

import electrichead.kotlinject.activation.ActivationContext
import kotlin.reflect.KClass

class WhenInjectedInto(target: KClass<*>) : IBindingCondition {
    val target = target

    override fun matches(context : ActivationContext): Boolean {
        val injectingInto = context.activationHistory.last().first

        if(injectingInto == target){
            return true
        }

        return false
    }
}