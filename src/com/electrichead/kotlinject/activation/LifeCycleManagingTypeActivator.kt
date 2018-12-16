package com.electrichead.kotlinject.activation

import com.electrichead.kotlinject.registration.Binding
import com.electrichead.kotlinject.registration.Lifecycle
import kotlin.reflect.KClass

class LifeCycleManagingTypeActivator(creator: IActivateTypes) : IActivateTypes {
    private var _instanceCache = mutableMapOf<KClass<*>, Any>()
    private var _activator : IActivateTypes = creator

    override fun create(
        bindings: List<Binding>,
        activationContext: ActivationContext
    ): Any {

        val binding = bindings.first()
        val requestedType = binding.sourceType

        if(binding.lifecycle == Lifecycle.Singleton){
            if(_instanceCache.containsKey(requestedType)) {
                return _instanceCache[requestedType]!!
            }
        }

        val instance = _activator.create(bindings, activationContext)

        if(binding.lifecycle == Lifecycle.Singleton){
            _instanceCache[requestedType] = instance
        }

        return instance
    }
}