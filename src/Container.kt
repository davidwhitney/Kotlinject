import TypeResolution.AutoDiscoveryResolver
import kotlin.reflect.KClass

class Container {

    val registrations = TypeRegistry()
    private var _ctorSelector: ConstructorSelector = ConstructorSelector()

    fun resolve(requestedType: KClass<*>): Any {

        var typeToCreate = registrations.selectTypeFor(requestedType)

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
    private var _autoDiscovery : AutoDiscoveryResolver = AutoDiscoveryResolver()
    private var _bindings = mutableMapOf<KClass<*>, KClass<*>>()

    fun bind(iface: KClass<*>, impl: KClass<*>) {
        _bindings[iface] = impl
    }

    fun selectTypeFor(requestedType: KClass<*>): KClass<*> {

        if(_bindings.containsKey(requestedType)){
            return _bindings[requestedType]!!
        }

        return _autoDiscovery.selectTypeFor(requestedType)
    }
}

