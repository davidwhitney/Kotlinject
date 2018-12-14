import TypeResolution.TypeResolver
import kotlin.reflect.KClass

class Container {

    private var _ctorSelector: ConstructorSelector = ConstructorSelector()
    private var _typeResolver: TypeResolver = TypeResolver()
    val registrations = TypeRegistry()

    fun resolve(requestedType: KClass<*>): Any {

        var typeToCreate = _typeResolver.selectTypeFor(requestedType)

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

class TypeRegistry {
    fun bind(iface: KClass<*>, impl: KClass<*>) {

    }
}

