package Registration

import Registration.Lifecycle
import kotlin.reflect.KClass

class Binding {
    var targetType : KClass<*>? = null
    var lifecycle : Lifecycle = Lifecycle.PerRequest

    constructor(targetType : KClass<*>, lifecycle: Lifecycle){
        this.targetType = targetType
        this.lifecycle = lifecycle
    }
}