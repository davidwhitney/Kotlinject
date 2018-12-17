package com.electrichead.kotlinject.registration

import com.electrichead.kotlinject.activation.MissingBindingException
import com.electrichead.kotlinject.registration.conditionalbinding.AlwaysMatches
import com.electrichead.kotlinject.registration.conditionalbinding.BindingConditions
import com.electrichead.kotlinject.registration.conditionalbinding.IBindingCondition
import com.electrichead.kotlinject.registration.packagescanning.AutoDiscovery
import com.electrichead.kotlinject.resolution.AutoDiscoveryResolver
import kotlin.reflect.KClass

class TypeRegistry {
    var autoDiscovery: Boolean = true

    @get:JvmName("scan")
    val scan: AutoDiscovery = AutoDiscovery(this)

    private var _autoDiscovery = AutoDiscoveryResolver()
    private var _bindings = mutableMapOf<KClass<*>, MutableList<Binding>>()

    inline fun <reified T1 : Any, reified T2 : Any> bind(
        noinline condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
        ): TypeRegistry {
        return bind(T1::class, T2::class, condition, lifecycle)
    }

    inline fun <reified T1 : Any> bind(
        noinline function: () -> Any,
        noinline condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
        ): TypeRegistry {
        return bind(T1::class, function, condition, lifecycle)
    }

    inline fun <reified T1 : Any> bindSelf(
        noinline condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
        ): TypeRegistry {
        return bind(T1::class, T1::class, condition, lifecycle)
    }

    @JvmOverloads
    fun bindSelf(
        self: KClass<*>,
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        return bind(self, self, condition, lifecycle)
    }

    @JvmOverloads
    fun bind(
        iface: KClass<*>,
        impl: KClass<*>? = null,
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        var target = impl
        var ls = lifecycle

        if (!iface.isAbstract) {
            target = iface
        }

        if (!_bindings.containsKey(iface)) {
            _bindings[iface] = mutableListOf()
        }

        if (ls == null) {
            ls = Lifecycle.PerRequest
        }

        if(target == null) target = iface

        var cond = condition
        if(condition == null) cond = { AlwaysMatches() }

        val binding = Binding(iface, target, ls, cond!!(BindingConditions()))
        _bindings[iface]!!.add(binding)
        return this
    }

    @JvmOverloads
    fun bind(
        type: KClass<*>,
        function: () -> Any,
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {

        if (!_bindings.containsKey(type)) {
            _bindings[type] = mutableListOf()
        }

        var ls = lifecycle
        if (ls == null) {
            ls = Lifecycle.PerRequest
        }

        var cond = condition
        if(condition == null) cond = { AlwaysMatches() }

        val binding = Binding(type, function, ls, cond!!.invoke(BindingConditions()))
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

    // For Java
    fun bind(
        iface: java.lang.Class<*>,
        impl: java.lang.Class<*>? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        return bind(iface, impl, null, lifecycle)
    }

    @JvmOverloads
    fun bind(
        iface: java.lang.Class<*>,
        impl: java.lang.Class<*>? = null,
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        val ifaceK = iface.kotlin
        var implK = impl?.kotlin

        if (!iface.isInterface) {
            implK = ifaceK
        }

        return bind(ifaceK, implK, condition, lifecycle)
    }

    fun bind(
        type: java.lang.Class<*>,
        function: () -> Any,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        return bind(type, function, null, lifecycle)
    }

    @JvmOverloads
    fun bind(
        type: java.lang.Class<*>,
        function: () -> Any,
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        return bind(type.kotlin, function, condition, lifecycle)
    }

    @JvmOverloads
    fun bindSelf(
        self: java.lang.Class<*>,
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        return bind(self, self, condition, lifecycle)
    }
    // End Java friendly overloads

}