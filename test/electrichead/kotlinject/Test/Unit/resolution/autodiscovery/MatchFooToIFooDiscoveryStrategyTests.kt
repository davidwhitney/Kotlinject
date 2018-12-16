package electrichead.kotlinject.Test.Unit.resolution.autodiscovery

import electrichead.kotlinject.Test.Unit.stubs.*
import electrichead.kotlinject.resolution.autodiscovery.MatchFooToIFooDiscoveryStrategy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MatchFooToIFooDiscoveryStrategyTests {

    private lateinit var _resolver: MatchFooToIFooDiscoveryStrategy

    @BeforeEach
    fun setUp() {
        _resolver = MatchFooToIFooDiscoveryStrategy()
    }

    @Test
    fun discover_passedIPrefixedType_ReturnsNonIPrefixAtSameLocation() {
        val target = _resolver.discover(IFoo::class)!!

        assertEquals(Foo::class, target)
    }

    @Test
    fun discover_passedIPrefixedTypeNoMatchingConcreteFound_ReturnsNull() {
        val target = _resolver.discover(INoneNameMatchingPair::class)

        assertNull(target)
    }

    @Test
    fun discover_MatchingNameFoundButDoesntImplementInterface_ReturnsNull() {
        val target = _resolver.discover(IBadImplementationCheck::class)

        assertNull(target)
    }
}

