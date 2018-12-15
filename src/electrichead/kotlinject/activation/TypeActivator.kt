package electrichead.kotlinject.activation

import electrichead.kotlinject.registration.Binding
import electrichead.kotlinject.registration.Lifecycle
import kotlin.reflect.KClass

class TypeActivator : IActivateTypes {
    private var _ctorSelector = ConstructorSelector()

    override fun create(binding: Binding): Any {
        val factoryCreated = binding.targetDelegate()
        if(factoryCreated != null){
            return factoryCreated
        }

        val typeToCreate = binding.targetType!!
        return createFromType(typeToCreate)
    }

    private fun createFromType(typeToCreate: KClass<*>): Any {
        val constructorToExecute = _ctorSelector.select(typeToCreate)
        val params = constructorToExecute.parameters.toList()

        val dependencies = mutableListOf<Any>()
        for (p in params) {
            val typeToResolve = p.type.classifier as KClass<*>
            val instance = create(Binding(typeToResolve, typeToResolve, Lifecycle.PerRequest))
            dependencies.add(instance)
        }

        return constructorToExecute.call(*dependencies.toTypedArray())
    }
}