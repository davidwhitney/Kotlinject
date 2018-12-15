package Kotlinject

import Kotlinject.Activation.ICreateTypes
import Kotlinject.Activation.LifeCycleManagingTypeActivator
import Kotlinject.Activation.TypeActivator
import Kotlinject.Registration.TypeRegistry
import kotlin.reflect.KClass

class Container {

    val registrations = TypeRegistry()
    private val creator : ICreateTypes

    init {
        creator = LifeCycleManagingTypeActivator(TypeActivator())
    }

    inline fun <reified T: Any> resolve(): T? {
        return resolve(T::class) as T
    }

    fun resolve(requestedType: KClass<*>): Any {

        val binding = registrations.selectTypeFor(requestedType)
        val instance = creator.create(binding)
        return instance
    }
}

