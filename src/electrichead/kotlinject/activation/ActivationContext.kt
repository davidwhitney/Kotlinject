package electrichead.kotlinject.activation

import kotlin.reflect.KClass

class ActivationContext(rootType : KClass<*>) {
    val rootType = rootType
    val activationHistory : MutableList<Pair<KClass<*>, KClass<*>>> = mutableListOf(Pair(rootType, rootType))
}