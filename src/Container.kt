import Activation.ICreateTypes
import Activation.LifeCycleManagingTypeActivator
import Activation.TypeActivator
import Registration.TypeRegistry
import kotlin.reflect.KClass

class Container {

    val registrations = TypeRegistry()
    private val creator : ICreateTypes

    constructor(){
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

