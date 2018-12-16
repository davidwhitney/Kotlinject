package com.electrichead.kotlinject.resolution

import com.electrichead.kotlinject.resolution.autoresolution.IAutoResolutionStrategy
import com.electrichead.kotlinject.resolution.autoresolution.ResolveConcreteTypeToSelfStrategy
import com.electrichead.kotlinject.resolution.autoresolution.ResolveFooToFooImplStrategy
import com.electrichead.kotlinject.resolution.autoresolution.ResolveIFooToFooStrategy
import kotlin.reflect.KClass

class AutoDiscoveryResolver {
    var strategies: MutableList<IAutoResolutionStrategy> = mutableListOf(
        ResolveConcreteTypeToSelfStrategy(),
        ResolveIFooToFooStrategy(),
        ResolveFooToFooImplStrategy()
    )

    fun selectTypeFor(requestedType: KClass<*>): KClass<*> {
        for (stat in strategies) {
            var match = stat.discover(requestedType)
            if (match != null) {
                return match
            }
        }
        return requestedType
    }
}

