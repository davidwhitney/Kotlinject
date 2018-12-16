package electrichead.kotlinject.resolution.autodiscovery

import kotlin.reflect.KClass

class MatchFooToIFooDiscoveryStrategy : IInterfaceDiscoveryStrategy {
    override fun discover(requestedType: KClass<*>): KClass<*>? {
        val simpleName = requestedType.simpleName
        val fqName = requestedType.qualifiedName

        if(simpleName != null && simpleName.startsWith("I")){
            val trimmed = simpleName.removeRange(0,1)
            val targetName = fqName!!.replace(simpleName, trimmed)

            val instance = createInstance(targetName)

            return when {
                instance != null && instance.java.interfaces.contains(requestedType.java) -> instance
                else -> null
            }
        }

        return null
    }

    private fun createInstance(targetName: String): KClass<out Any>? {
        return try {
            Class.forName(targetName).kotlin
        } catch (ex: ClassNotFoundException) {
            null
        }
    }
}