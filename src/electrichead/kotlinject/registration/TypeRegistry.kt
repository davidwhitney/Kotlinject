package electrichead.kotlinject.registration

import electrichead.kotlinject.activation.MissingBindingException
import electrichead.kotlinject.resolution.AutoDiscoveryResolver
import kotlin.reflect.KClass

class TypeRegistry {

    var autoDiscovery : Boolean = true
    private var _autoDiscovery = AutoDiscoveryResolver()
    private var _bindings = mutableMapOf<KClass<*>, Binding>()

    fun bind(iface: KClass<*>, impl: KClass<*>, lifecycle: Lifecycle = Lifecycle.PerRequest) : TypeRegistry {
        _bindings[iface] = Binding(impl, lifecycle)
        return this
    }

    fun selectTypeFor(requestedType: KClass<*>): Binding {
        if(_bindings.containsKey(requestedType)){
            return _bindings[requestedType]!!
        }

        if(autoDiscovery) {
            val type = _autoDiscovery.selectTypeFor(requestedType)
            return Binding(type, Lifecycle.PerRequest)
        }

        throw MissingBindingException("No bindings found for: " + requestedType.qualifiedName)
    }
}

