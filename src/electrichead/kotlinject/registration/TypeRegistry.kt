package electrichead.kotlinject.registration

import electrichead.kotlinject.activation.MissingBindingException
import electrichead.kotlinject.registration.packagescanning.AutoDiscovery
import electrichead.kotlinject.resolution.AutoDiscoveryResolver
import kotlin.reflect.KClass

class TypeRegistry {

    var autoDiscovery : Boolean = true
    val scan : AutoDiscovery = AutoDiscovery(this)

    private var _autoDiscovery = AutoDiscoveryResolver()
    private var _bindings = mutableMapOf<KClass<*>, Binding>()

    inline fun <reified T1: Any, reified T2: Any> bind(lifecycle: Lifecycle = Lifecycle.PerRequest) : TypeRegistry {
        return bind(T1::class, T2::class, lifecycle)
    }

    fun bind(iface: KClass<*>, impl: KClass<*>? = null, lifecycle: Lifecycle = Lifecycle.PerRequest) : TypeRegistry {
        var target = impl

        if(target == null && !iface.isAbstract) {
            target = iface
        }

        _bindings[iface] = Binding(iface, target!!, lifecycle)
        return this
    }


    inline fun <reified T1: Any> bind(noinline function: () -> Any, lifecycle: Lifecycle = Lifecycle.PerRequest) : TypeRegistry {
        return bind(T1::class, function, lifecycle)
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

