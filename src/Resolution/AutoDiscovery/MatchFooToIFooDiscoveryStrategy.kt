package Resolution.AutoDiscovery

import kotlin.reflect.KClass

class MatchFooToIFooDiscoveryStrategy : InterfaceDiscoveryStrategy {
    override fun discover(requestedType: KClass<*>): KClass<*>? {
        var simpleName = requestedType.simpleName
        var fqName = requestedType.qualifiedName

        if(simpleName != null && simpleName.startsWith("I")){
            var trimmed = simpleName.removeRange(0,1)
            var targetName = fqName!!.replace(simpleName, trimmed)
            return Class.forName(targetName).kotlin
        }
        return null
    }
}