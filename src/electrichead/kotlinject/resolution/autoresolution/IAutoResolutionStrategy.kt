package electrichead.kotlinject.resolution.autoresolution

import kotlin.reflect.KClass

interface IAutoResolutionStrategy{
    fun discover(requestedType: KClass<*>): KClass<*>?
}