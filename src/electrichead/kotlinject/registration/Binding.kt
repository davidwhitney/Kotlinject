package electrichead.kotlinject.registration

import kotlin.reflect.KClass

class Binding {
    var sourceType : KClass<*>? = null
    var targetType : KClass<*>? = null
    var lifecycle : Lifecycle = Lifecycle.PerRequest
    var targetDelegate: () -> Any? = { null }

    constructor(sourceType : KClass<*>, targetType : KClass<*>, lifecycle: Lifecycle){
        this.sourceType = sourceType
        this.targetType = targetType
        this.lifecycle = lifecycle
    }

    constructor(sourceType : KClass<*>, targetDelegate: () -> Any, lifecycle: Lifecycle){
        this.sourceType = sourceType
        this.targetDelegate = targetDelegate
        this.lifecycle = lifecycle
    }
}