package electrichead.kotlinject

import electrichead.kotlinject.activation.IActivateTypes
import electrichead.kotlinject.activation.LifeCycleManagingTypeActivator
import electrichead.kotlinject.activation.TypeActivator
import electrichead.kotlinject.registration.TypeRegistry
import kotlin.reflect.KClass

class Container {

    val registrations = TypeRegistry()
    private val creator : IActivateTypes

    init {
        creator = LifeCycleManagingTypeActivator(TypeActivator())
    }

    inline fun <reified T: Any> resolve(): T? {
        return resolve(T::class) as T
    }

    fun resolve(requestedType: KClass<*>): Any {

        val binding = registrations.retrieveBindingFor(requestedType)

        try {
            return creator.create(binding)
        } catch (ex : StackOverflowError){
            throw CircularDependencyException(ex)
        }
    }
}

