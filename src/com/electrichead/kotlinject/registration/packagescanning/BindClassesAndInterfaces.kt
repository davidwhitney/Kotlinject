package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.TypeRegistry

class BindClassesAndInterfaces : IBindingStrategy {
    private var _classes: BindClassesToSelf = BindClassesToSelf()
    private var _interfaces: BindAllInterfaces = BindAllInterfaces()

    override fun bind(typeRegistry: TypeRegistry, classes: Array<Class<*>>) {
        _classes.bind(typeRegistry, classes)
        _interfaces.bind(typeRegistry, classes)
    }
}