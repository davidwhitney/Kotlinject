package Activation

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KVisibility

class ConstructorSelector {
    fun select(requestedType: KClass<*>): KFunction<Any> {
        var selected = requestedType.constructors.first()
        val largestCtor = selected.parameters.count()

        for (ctor: KFunction<Any> in requestedType.constructors) {
            if (ctor.visibility == KVisibility.PUBLIC) {
                if (ctor.parameters.count() > largestCtor) {
                    selected = ctor
                }
            }
        }
        return selected
    }
}