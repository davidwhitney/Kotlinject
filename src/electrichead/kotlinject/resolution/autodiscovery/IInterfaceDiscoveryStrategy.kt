package electrichead.kotlinject.resolution.autodiscovery

import kotlin.reflect.KClass

interface IInterfaceDiscoveryStrategy{
    fun discover(requestedType: KClass<*>): KClass<*>?
}