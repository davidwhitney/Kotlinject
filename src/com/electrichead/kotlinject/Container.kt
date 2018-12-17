package com.electrichead.kotlinject

import com.electrichead.kotlinject.activation.ActivationContext
import com.electrichead.kotlinject.activation.IActivateTypes
import com.electrichead.kotlinject.activation.LifeCycleManagingTypeActivator
import com.electrichead.kotlinject.activation.TypeActivator
import com.electrichead.kotlinject.registration.TypeRegistry
import kotlin.reflect.KClass

class Container {

    val registrations = TypeRegistry()
    private val creator : IActivateTypes

    init {
        creator = LifeCycleManagingTypeActivator(TypeActivator(registrations))
    }

    inline fun <reified T: Any> resolve(): T {
        return resolve(T::class) as T
    }

    fun resolve(requestedType: KClass<*>): Any {
        val bindings = registrations.retrieveBindingFor(requestedType)
        val activationContext = ActivationContext(requestedType)

        try {
            return creator.create(bindings, activationContext)
        } catch (ex : StackOverflowError){
            throw CircularDependencyException(ex)
        }
    }

    // For Java
    fun resolve(requestedType: java.lang.Class<*>) : Any {
        return resolve(requestedType.kotlin)
    }
}

