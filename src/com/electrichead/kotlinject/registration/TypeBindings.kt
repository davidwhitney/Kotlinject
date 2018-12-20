package com.electrichead.kotlinject.registration

import kotlin.reflect.KClass

class TypeBindings {
    private var _addLock = Object()
    private var _bindings = mutableMapOf<KClass<*>, MutableList<Binding>>()

    fun hasMappingFor(type : KClass<*>) : Boolean = _bindings.containsKey(type)

    fun add(type : KClass<*>, binding : Binding) {
        synchronized(_addLock) {
            if (!hasMappingFor(type)) {
                _bindings[type] = mutableListOf()
            }

            _bindings[type]!!.add(binding)
        }
    }

    fun get(type : KClass<*>) : List<Binding> = _bindings[type]!!.toList()
}