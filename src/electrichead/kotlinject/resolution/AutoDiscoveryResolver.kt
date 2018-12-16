package electrichead.kotlinject.resolution

import electrichead.kotlinject.resolution.autodiscovery.IInterfaceDiscoveryStrategy
import electrichead.kotlinject.resolution.autodiscovery.MatchFooToFooImplDiscoveryStrategy
import electrichead.kotlinject.resolution.autodiscovery.MatchFooToIFooDiscoveryStrategy
import kotlin.reflect.KClass

class AutoDiscoveryResolver {

    var strategies: MutableList<IInterfaceDiscoveryStrategy> = mutableListOf(
        MatchFooToIFooDiscoveryStrategy(),
        MatchFooToFooImplDiscoveryStrategy()
    )

    fun selectTypeFor(requestedType: KClass<*>): KClass<*> {
        if (!requestedType.isAbstract) {
            return requestedType
        }

        for (stat in strategies) {
            var match = stat.discover(requestedType)
            if (match != null) {
                return match
            }
        }
        return requestedType
    }
}

