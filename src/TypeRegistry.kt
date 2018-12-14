import TypeResolution.AutoDiscoveryResolver
import kotlin.reflect.KClass

class TypeRegistry {
    private var _autoDiscovery : AutoDiscoveryResolver = AutoDiscoveryResolver()
    private var _bindings = mutableMapOf<KClass<*>, KClass<*>>()

    fun bind(iface: KClass<*>, impl: KClass<*>) {
        _bindings[iface] = impl
    }

    fun selectTypeFor(requestedType: KClass<*>): KClass<*> {
        if(_bindings.containsKey(requestedType)){
            return _bindings[requestedType]!!
        }

        return _autoDiscovery.selectTypeFor(requestedType)
    }
}