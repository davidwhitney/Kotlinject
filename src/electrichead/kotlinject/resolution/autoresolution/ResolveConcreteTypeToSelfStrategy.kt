package electrichead.kotlinject.resolution.autoresolution

import kotlin.reflect.KClass

class ResolveConcreteTypeToSelfStrategy : IAutoResolutionStrategy {
    override fun discover(requestedType: KClass<*>): KClass<*>? {
        when {
            !requestedType.isAbstract -> return requestedType
            else -> return null
        }
    }

}