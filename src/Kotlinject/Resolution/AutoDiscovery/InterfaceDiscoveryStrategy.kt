package Kotlinject.Resolution.AutoDiscovery

import kotlin.reflect.KClass

interface InterfaceDiscoveryStrategy{
    fun discover(requestedType: KClass<*>): KClass<*>?
}