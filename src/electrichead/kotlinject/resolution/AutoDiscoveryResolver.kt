package electrichead.kotlinject.resolution

import electrichead.kotlinject.resolution.autodiscovery.InterfaceDiscoveryStrategy
import electrichead.kotlinject.resolution.autodiscovery.MatchFooToFooImplDiscoveryStrategy
import electrichead.kotlinject.resolution.autodiscovery.MatchFooToIFooDiscoveryStrategy
import kotlin.reflect.KClass

class AutoDiscoveryResolver {

    private var _strategies: List<InterfaceDiscoveryStrategy> = listOf(
        MatchFooToIFooDiscoveryStrategy(),
        MatchFooToFooImplDiscoveryStrategy()
    )

    fun selectTypeFor(requestedType: KClass<*>): KClass<*> {
        if (!requestedType.isAbstract) {
            return requestedType
        }

        for (stat in _strategies) {
            var match = stat.discover(requestedType)
            if (match != null) {
                return match
            }
        }
        return requestedType
    }
}

