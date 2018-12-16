package electrichead.kotlinject.Test.Unit.resolution.autodiscovery

import electrichead.kotlinject.Test.Unit.stubs.Blah
import electrichead.kotlinject.Test.Unit.stubs.Blah2
import electrichead.kotlinject.Test.Unit.stubs.BlahImpl
import electrichead.kotlinject.Test.Unit.stubs2.BarBar
import electrichead.kotlinject.resolution.autodiscovery.MatchFooToFooImplDiscoveryStrategy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MatchFooToFooImplDiscoveryStrategyTests {

    private lateinit var _resolver: MatchFooToFooImplDiscoveryStrategy

    @BeforeEach
    fun setUp() {
        _resolver = MatchFooToFooImplDiscoveryStrategy()
    }

    @Test
    fun discover_passedTypeWithMatchingImpl_ReturnsMatchingImpl() {
        val expectedTarget = _resolver.discover(Blah::class)!!

        assertEquals(BlahImpl::class, expectedTarget)
    }

    @Test
    fun discover_passedTypeNoMatchingConcreteImplFound_ReturnsNull() {
        val target = _resolver.discover(BarBar::class)

        assertNull(target)
    }

    @Test
    fun discover_MatchingNameFoundButDoesntImplementInterface_ReturnsNull() {
        val target = _resolver.discover(Blah2::class)

        assertNull(target)
    }
}