package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.TypeRegistry
import kotlin.reflect.KClass

class BindClassesAndInterfaces : IBindingStrategy {
    private var _classes: BindClassesToSelf = BindClassesToSelf()
    private var _interfaces: BindAllInterfaces = BindAllInterfaces()

    override fun bind(typeRegistry: TypeRegistry, classes: List<KClass<*>>) {
        _classes.bind(typeRegistry, classes)
        _interfaces.bind(typeRegistry, classes)
    }
}