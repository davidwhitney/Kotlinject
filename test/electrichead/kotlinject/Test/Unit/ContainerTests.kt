package electrichead.kotlinject.Test.Unit

import electrichead.kotlinject.CircularDependencyException
import electrichead.kotlinject.Container
import electrichead.kotlinject.Test.Unit.stubs.*
import electrichead.kotlinject.registration.Lifecycle
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
    fun resolve_ResolvesTypesFromDelegates() {
        val foo = Foo()
        _container.registrations.bind(Foo::class, { foo })

        val instance = _container.resolve(Foo::class)

        assertEquals(foo, instance)
    }

    @Test
    fun resolve_TypeBoundAsSingleton_ResolvesSameInstanceTwice() {
        val instance1 = _container.resolve(IBar::class)
        val instance2 = _container.resolve(IBar::class)
        assertEquals(instance1, instance2)
    }

    @Test
    fun resolve_TypeWithCircularDependency_Throws() {
        _container.registrations.bind(TypeWithACircularDep::class)
        _container.registrations.bind(TypeWithACircularDep2::class)
        assertThrows<CircularDependencyException> { _container.resolve(TypeWithACircularDep::class) }
    }
}

