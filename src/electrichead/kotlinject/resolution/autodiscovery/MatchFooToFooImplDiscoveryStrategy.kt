package electrichead.kotlinject.resolution.autodiscovery

import kotlin.reflect.KClass

class MatchFooToFooImplDiscoveryStrategy : IInterfaceDiscoveryStrategy {
    override fun discover(requestedType: KClass<*>): KClass<*>? {
        val fqName = requestedType.qualifiedName

        if (requestedType.isAbstract) {
            val targetName = fqName + "Impl"

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