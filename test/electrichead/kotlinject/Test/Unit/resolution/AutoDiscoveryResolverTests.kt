package electrichead.kotlinject.Test.Unit.resolution

import electrichead.kotlinject.Test.Unit.stubs.Foo
import electrichead.kotlinject.Test.Unit.stubs.IFoo
import electrichead.kotlinject.resolution.AutoDiscoveryResolver
import electrichead.kotlinject.resolution.autodiscovery.IInterfaceDiscoveryStrategy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class AutoDiscoveryResolverTests {

    private lateinit var _resolver: AutoDiscoveryResolver

    @BeforeEach
    fun setUp() {
        _resolver = AutoDiscoveryResolver()
    }

    @Test
    fun selectTypeFor_PassedConcreteType_ReturnsThatType() {
        val typeDef = _resolver.selectTypeFor(Foo::class)

        assertEquals(Foo::class, typeDef)
    }

    @Test
    fun selectTypeFor_PassedInterface_DelegatesToExternalStrategies() {
        val spy = DiscoveryStrategySpy()
        _resolver.strategies.clear()
        _resolver.strategies.add(spy)

        _resolver.selectTypeFor(IFoo::class)

        assertTrue(spy.wasCalled)
    }

    class DiscoveryStrategySpy : IInterfaceDiscoveryStrategy {
        var wasCalled = false
        override fun discover(requestedType: KClass<*>): KClass<*>? {
            wasCalled = true
            return null
        }
    }
}