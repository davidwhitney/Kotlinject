package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.TypeRegistry
import kotlin.reflect.KClass

class BindAllInterfaces : IBindingStrategy {
    override fun bind(typeRegistry: TypeRegistry, classes: List<KClass<*>>) {

        for(clazz in classes) {
            if(!clazz.java.isInterface) {
                val allInterfaces = clazz.java.interfaces

                for(iface in allInterfaces){
                    typeRegistry.bind(iface.kotlin, clazz)
                }
            }
        }
    }
}

