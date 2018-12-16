package electrichead.kotlinject.Test.Unit.resolution

import electrichead.kotlinject.Test.Unit.stubs.IFoo
import electrichead.kotlinject.resolution.AutoDiscoveryResolver
import electrichead.kotlinject.resolution.autoresolution.IAutoResolutionStrategy
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass
import kotlin.test.assertTrue

class AutoDiscoveryResolverTests {
    @Test
    fun selectTypeFor_PassedInterface_DelegatesToExternalStrategies() {
        var resolver = AutoDiscoveryResolver()
        val spy = DiscoveryStrategySpy()
        resolver.strategies.clear()
        resolver.strategies.add(spy)

        resolver.selectTypeFor(IFoo::class)

        assertTrue(spy.wasCalled)
    }

    class DiscoveryStrategySpy : IAutoResolutionStrategy {
        var wasCalled = false
        override fun discover(requestedType: KClass<*>): KClass<*>? {
            wasCalled = true
            return null
        }
    }
}