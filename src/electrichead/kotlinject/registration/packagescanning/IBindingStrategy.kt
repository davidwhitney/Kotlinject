package electrichead.kotlinject.registration.packagescanning

import electrichead.kotlinject.registration.TypeRegistry

interface IBindingStrategy {
    fun bind(typeRegistry: TypeRegistry, classes : Array<Class<*>>)
}