package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.TypeRegistry
import kotlin.reflect.KClass

interface IBindingStrategy {
    fun bind(typeRegistry: TypeRegistry, classes : List<KClass<*>>)
}