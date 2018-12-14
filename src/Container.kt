import kotlin.reflect.KClass

class Container {

    val registrations = TypeRegistry()
    private var _ctorSelector: ConstructorSelector = ConstructorSelector()

    fun resolve(requestedType: KClass<*>): Any {

        val typeToCreate = registrations.selectTypeFor(requestedType)

        val constructorToExecute = _ctorSelector.select(typeToCreate)
        val params = constructorToExecute.parameters.toList()

        val dependencies = mutableListOf<Any>()
        for(p in params){
            val typeToResolve = p.type.classifier as KClass<*>
            val instance = resolve(typeToResolve)
            dependencies.add(instance)
        }

        return constructorToExecute.call(*dependencies.toTypedArray())
    }
}

