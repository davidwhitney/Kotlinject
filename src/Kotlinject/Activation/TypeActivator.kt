package Kotlinject.Activation

import Kotlinject.Registration.Binding
import Kotlinject.Registration.Lifecycle
import kotlin.reflect.KClass

class TypeActivator : ICreateTypes {
    private var _ctorSelector = ConstructorSelector()

    override fun create(binding: Binding): Any {

        val typeToCreate = binding.targetType!!

        val constructorToExecute = _ctorSelector.select(typeToCreate)
        val params = constructorToExecute.parameters.toList()

        val dependencies = mutableListOf<Any>()
        for (p in params) {
            val typeToResolve = p.type.classifier as KClass<*>
            val instance = create(Binding(typeToResolve, Lifecycle.PerRequest))
            dependencies.add(instance)
        }

        return constructorToExecute.call(*dependencies.toTypedArray())
    }
}