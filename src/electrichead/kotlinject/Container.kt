package electrichead.kotlinject

import electrichead.kotlinject.activation.ICreateTypes
import electrichead.kotlinject.activation.LifeCycleManagingTypeActivator
import electrichead.kotlinject.activation.TypeActivator
import electrichead.kotlinject.registration.TypeRegistry
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

