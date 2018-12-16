package electrichead.kotlinject.activation

import electrichead.kotlinject.registration.Binding
import electrichead.kotlinject.registration.TypeRegistry
import kotlin.reflect.KClass

class TypeActivator(typeRegistry: TypeRegistry) : IActivateTypes {
    private var _typeRegistry = typeRegistry
    private var _ctorSelector = ConstructorSelector()

    override fun create(
        bindings: List<Binding>,
        activationContext: ActivationContext
    ): Any {

        val binding = bindings.last { x -> x.condition!!.matches(activationContext) }

        val factoryCreated = binding.targetDelegate()
        if (factoryCreated != null) {
            return factoryCreated
        }

        val typeToCreate = binding.targetType!!
        return createFromType(typeToCreate, activationContext)
    }

    private fun createFromType(
        typeToCreate: KClass<*>,
        activationContext: ActivationContext
    ): Any {

        val constructorToExecute = _ctorSelector.select(typeToCreate)
        val params = constructorToExecute.parameters.toList()

        val dependencies = mutableListOf<Any>()
        for (p in params) {
            val paramToCreate = p.type.classifier as KClass<*>
            val bindings = _typeRegistry.retrieveBindingFor(paramToCreate)

            activationContext.activationHistory.add(Pair(typeToCreate, paramToCreate))

            val instance = create(bindings, activationContext)
            dependencies.add(instance)
        }

        return constructorToExecute.call(*dependencies.toTypedArray())
    }
}