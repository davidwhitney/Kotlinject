package electrichead.kotlinject.registration.packagescanning

import electrichead.kotlinject.registration.TypeRegistry

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

class BindClassesToSelf : IBindingStrategy {
    override fun bind(typeRegistry: TypeRegistry, classes: Array<Class<*>>) {

        for(clazz in classes) {
            if(!clazz.isInterface) {
                val allInterfaces = clazz.interfaces
                for(iface in allInterfaces){
                    typeRegistry.bind(clazz.kotlin)
                }
            }
        }
    }
}