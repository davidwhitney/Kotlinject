import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KVisibility

class ConstructorSelector {
    fun select(requestedType: KClass<*>): KFunction<Any> {
        var largestCtor = 0
        var constructorToExecute: KFunction<Any> =
            requestedType.constructors.first()

        for (ctor: KFunction<Any> in requestedType.constructors) {
            if (ctor.visibility != KVisibility.PUBLIC) {
                if (ctor.parameters.count() >= largestCtor) {
                    constructorToExecute = ctor
                }
            }
        }
        return constructorToExecute
    }
}