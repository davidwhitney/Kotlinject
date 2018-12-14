package TypeResolution

import TypeResolution.InterfaceAutoDiscoveryStrategies.*
import kotlin.reflect.*

class TypeResolver {

    private var _strategies: List<InterfaceDiscoveryStrategy>
            = listOf<InterfaceDiscoveryStrategy>(
        MatchFooToIFooDiscoveryStrategy()
    )

    fun selectTypeFor(requestedType: KClass<*>): KClass<*> {
        if(!requestedType.isAbstract){
            return requestedType
        }

        for(stat in _strategies) {
            var match = stat.discover(requestedType)
            if (match != null) {
                return match
            }
        }
        return requestedType
    }
}

