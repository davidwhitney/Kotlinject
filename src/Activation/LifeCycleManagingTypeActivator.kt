package Activation

import Registration.Binding
import Registration.Lifecycle
import kotlin.reflect.KClass

class LifeCycleManagingTypeActivator(creator: ICreateTypes) : ICreateTypes {
    private var _instanceCache = mutableMapOf<KClass<*>, Any>()
    private var _creator : ICreateTypes = creator

    override fun create(binding: Binding): Any {
        val typeToCreate = binding.targetType!!

        if(binding.lifecycle == Lifecycle.Singleton){
            if(_instanceCache.containsKey(typeToCreate)) {
                return _instanceCache[typeToCreate]!!
            }
        }

        var instance = _creator.create(binding)

        if(binding.lifecycle == Lifecycle.Singleton){
            _instanceCache[typeToCreate] = instance
        }

        return instance
    }
}