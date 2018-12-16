package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.TypeRegistry

interface IBindingStrategy {
    fun bind(typeRegistry: TypeRegistry, classes : Array<Class<*>>)
}