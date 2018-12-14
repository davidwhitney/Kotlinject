package TypeResolution.InterfaceAutoDiscoveryStrategies

import kotlin.reflect.KClass

interface InterfaceDiscoveryStrategy{
    fun discover(requestedType: KClass<*>): KClass<*>?
}