package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.TypeRegistry

class BindAllInterfaces : IBindingStrategy {
    override fun bind(typeRegistry: TypeRegistry, classes: Array<Class<*>>) {

        for(clazz in classes) {
            if(!clazz.isInterface) {
                val allInterfaces = clazz.interfaces

                for(iface in allInterfaces){
                    typeRegistry.bind(iface.kotlin, clazz.kotlin)
                }
            }
        }
    }
}

