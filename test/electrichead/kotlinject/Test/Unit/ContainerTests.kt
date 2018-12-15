package electrichead.kotlinject.Test.Unit

import electrichead.kotlinject.Container
import electrichead.kotlinject.registration.Lifecycle
import electrichead.kotlinject.Test.Unit.stubs.Bar
import electrichead.kotlinject.Test.Unit.stubs.Foo
import electrichead.kotlinject.Test.Unit.stubs.IBar
import electrichead.kotlinject.Test.Unit.stubs.IFoo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ContainerTests {

    private lateinit var _container: Container

    @BeforeEach
    fun setUp(){
        _container = Container()
        _container.registrations.autoDiscovery = false
        _container.registrations.bind(IFoo::class, Foo::class)
        _container.registrations.bind(IBar::class, Bar::class, Lifecycle.Singleton)
    }

    @Test
    fun resolveT_ResolvesTypesFromRegistry() {
        val instance = _container.resolve<IFoo>()
        assertNotNull(instance)
    }

    @Test
    fun resolve_ResolvesTypesFromRegistry() {
        val instance = _container.resolve(IFoo::class)
        assertNotNull(instance)
    }

    @Test
    fun resolve_TypeBoundAsSingleton_ResolvesSameInstanceTwice() {
        val instance1 = _container.resolve(IBar::class)
        val instance2 = _container.resolve(IBar::class)

        assertEquals(instance1, instance2)
    }

}

