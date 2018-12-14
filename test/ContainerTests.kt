package test

import Container
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import test.Stubs.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ContainerTests {

    private lateinit var _container: Container

    @BeforeEach
    fun setUp(){
        _container = Container()
    }

    @Test
    fun resolve_NoBindings_CanResolveTypeWithNoDependencies() {
        var instance = _container.resolve(Foo::class)

        assertNotNull(instance)
    }

    @Test
    fun resolve_NoBindings_CanResolveTypeWithDependencies(){
        var instance = _container.resolve(TypeWithDependency::class)

        assertNotNull(instance)
    }

    @Test
    fun resolve_NoBindings_CanResolveInterfaceWithDefaultImplementation(){
        var instance = _container.resolve(IFoo::class)

        assertNotNull(instance)
    }

    @Test
    fun resolve_bindingPresent_CanResolveToBoundImplementation(){
        _container.registrations.bind(IFoo::class, Foo2::class)

        var instance = _container.resolve(IFoo::class)

        assertEquals("Foo2", instance::class.simpleName)
    }

    @Test
    fun resolve_chainBindingPresent_CanResolveToBoundImplementation(){
        _container.registrations
            .bind(IFoo::class, Foo2::class)
            .bind(TypeWithDependency::class, TypeWithDependency::class)

        var instance = _container.resolve(IFoo::class)

        assertEquals("Foo2", instance::class.simpleName)
    }
}