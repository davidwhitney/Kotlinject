package electrichead.kotlinject.registration

import electrichead.kotlinject.activation.MissingBindingException
import electrichead.kotlinject.registration.conditionalbinding.AlwaysMatches
import electrichead.kotlinject.registration.conditionalbinding.BindingConditions
import electrichead.kotlinject.registration.conditionalbinding.IBindingCondition
import electrichead.kotlinject.registration.packagescanning.AutoDiscovery
import electrichead.kotlinject.resolution.AutoDiscoveryResolver
import kotlin.reflect.KClass

class TypeRegistry {

    var autoDiscovery: Boolean = true
    val scan: AutoDiscovery = AutoDiscovery(this)

    private var _autoDiscovery = AutoDiscoveryResolver()
    private var _bindings = mutableMapOf<KClass<*>, MutableList<Binding>>()

    inline fun <reified T1 : Any, reified T2 : Any> bind(
        lifecycle: Lifecycle = Lifecycle.PerRequest,
        noinline condition: ((op: BindingConditions) -> IBindingCondition) = { c -> c.alwaysMatches() }
    ): TypeRegistry {
        return bind(T1::class, T2::class, lifecycle, condition)
    }

    inline fun <reified T1 : Any> bind(
        noinline function: () -> Any,
        lifecycle: Lifecycle = Lifecycle.PerRequest,
        noinline condition: ((op: BindingConditions) -> IBindingCondition) = { c -> c.alwaysMatches() }
    ): TypeRegistry {
        return bind(T1::class, function, lifecycle, condition)
    }

    inline fun <reified T1 : Any> bindSelf(
        lifecycle: Lifecycle = Lifecycle.PerRequest,
        noinline condition: ((op: BindingConditions) -> IBindingCondition) = { c -> c.alwaysMatches() }
    ): TypeRegistry {
        return bind(T1::class, T1::class, lifecycle, condition)
    }

    fun bind(
        iface: KClass<*>,
        impl: KClass<*>? = null,
        lifecycle: Lifecycle = Lifecycle.PerRequest,
        condition: ((op: BindingConditions) -> IBindingCondition) = { c -> c.alwaysMatches() }
    ): TypeRegistry {
        var target = impl

        if (target == null && !iface.isAbstract) {
            target = iface
        }

        if (!_bindings.containsKey(iface)) {
            _bindings[iface] = mutableListOf()
        }

        val binding = Binding(iface, target!!, lifecycle, condition(BindingConditions()))
        _bindings[iface]!!.add(binding)
        return this
    }

    fun bind(
        type: KClass<*>,
        function: () -> Any,
        lifecycle: Lifecycle = Lifecycle.PerRequest,
        condition: ((op: BindingConditions) -> IBindingCondition) = { AlwaysMatches() }
    ): TypeRegistry {

        if (!_bindings.containsKey(type)) {
            _bindings[type] = mutableListOf()
        }

        val binding = Binding(type, function, lifecycle, condition(BindingConditions()))
        _bindings[type]!!.add(binding)
        return this
    }

    fun retrieveBindingFor(requestedType: KClass<*>): List<Binding> {
        if (_bindings.containsKey(requestedType)) {
            return _bindings[requestedType]!!
        }

        if (autoDiscovery) {
            val type = _autoDiscovery.selectTypeFor(requestedType)
            return listOf(Binding(requestedType, type, Lifecycle.PerRequest))
        }

        throw MissingBindingException("No bindings found for: " + requestedType.qualifiedName)
    }
}