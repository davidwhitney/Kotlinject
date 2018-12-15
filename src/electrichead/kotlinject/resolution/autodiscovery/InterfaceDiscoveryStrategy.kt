package electrichead.kotlinject.resolution.autodiscovery

import kotlin.reflect.KClass

interface InterfaceDiscoveryStrategy{
    fun discover(requestedType: KClass<*>): KClass<*>?
}