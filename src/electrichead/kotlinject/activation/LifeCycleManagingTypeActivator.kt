package electrichead.kotlinject.activation

import electrichead.kotlinject.registration.Binding
import electrichead.kotlinject.registration.Lifecycle
import kotlin.reflect.KClass

class LifeCycleManagingTypeActivator(creator: IActivateTypes) : IActivateTypes {
    private var _instanceCache = mutableMapOf<KClass<*>, Any>()
    private var _activator : IActivateTypes = creator

    override fun create(binding: Binding): Any {
        val requestedType = binding.sourceType!!

        if(binding.lifecycle == Lifecycle.Singleton){
            if(_instanceCache.containsKey(requestedType)) {
                return _instanceCache[requestedType]!!
            }
        }

        var instance = _activator.create(binding)

        if(binding.lifecycle == Lifecycle.Singleton){
            _instanceCache[requestedType] = instance
        }

        return instance
    }
}