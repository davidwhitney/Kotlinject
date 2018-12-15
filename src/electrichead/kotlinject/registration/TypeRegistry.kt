package electrichead.kotlinject.registration

import electrichead.kotlinject.activation.MissingBindingException
import electrichead.kotlinject.resolution.AutoDiscoveryResolver
import kotlin.reflect.KClass

class TypeRegistry {

    var autoDiscovery : Boolean = true
    private var _autoDiscovery = AutoDiscoveryResolver()
    private var _bindings = mutableMapOf<KClass<*>, Binding>()

    fun bind(iface: KClass<*>, impl: KClass<*>? = null, lifecycle: Lifecycle = Lifecycle.PerRequest) : TypeRegistry {
        var target = impl

        if(target == null && !iface.isAbstract) {
            target = iface
        }

        _bindings[iface] = Binding(iface, target!!, lifecycle)
        return this
    }

    fun bind(type: KClass<*>, function: () -> Any, lifecycle: Lifecycle = Lifecycle.PerRequest) : TypeRegistry {
        _bindings[type] = Binding(type, function, lifecycle)
        return this
    }

    fun retrieveBindingFor(requestedType: KClass<*>): Binding {
        if(_bindings.containsKey(requestedType)){
            return _bindings[requestedType]!!
        }

        if(autoDiscovery) {
            val type = _autoDiscovery.selectTypeFor(requestedType)
            return Binding(requestedType, type, Lifecycle.PerRequest)
        }

        throw MissingBindingException("No bindings found for: " + requestedType.qualifiedName)
    }
}

