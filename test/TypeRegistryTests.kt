package test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import Kotlinject.Registration.TypeRegistry
import Kotlinject.Activation.MissingBindingException
import org.junit.jupiter.api.assertThrows
import test.Stubs.Foo
import test.Stubs.Foo2
import test.Stubs.IFoo
import test.Stubs.TypeWithDependency
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TypeRegistryTests{

    private lateinit var _registry: TypeRegistry

    @BeforeEach
    fun setUp(){
        _registry = TypeRegistry()
    }

    @Test
    fun selectTypeFor_NoBindingsNoDiscovery_Throws() {
        _registry.autoDiscovery = false

        assertThrows<MissingBindingException> {
            _registry.selectTypeFor(Foo::class)
        }

    }

    @Test
    fun selectTypeFor_NoBindings_CanResolveTypeWithNoDependencies() {
        val instance = _registry.selectTypeFor(Foo::class)

        assertNotNull(instance.targetType)
    }

    @Test
    fun selectTypeFor_NoBindings_CanResolveTypeWithDependencies(){
        val instance = _registry.selectTypeFor(TypeWithDependency::class)

        assertNotNull(instance.targetType)
    }

    @Test
    fun selectTypeFor_NoBindings_CanResolveInterfaceWithDefaultImplementation(){
        val instance = _registry.selectTypeFor(IFoo::class)

        assertNotNull(instance.targetType)
    }

    @Test
    fun selectTypeFor_bindingPresent_CanResolveToBoundImplementation(){
        _registry.bind(IFoo::class, Foo2::class)

        val instance = _registry.selectTypeFor(IFoo::class)

        assertEquals("Foo2", instance.targetType!!.simpleName)
    }

    @Test
    fun selectTypeFor_chainBindingPresent_CanResolveToBoundImplementation(){
        _registry
            .bind(IFoo::class, Foo2::class)
            .bind(TypeWithDependency::class, TypeWithDependency::class)

        val instance = _registry.selectTypeFor(IFoo::class)

        assertEquals("Foo2", instance.targetType!!.simpleName)
    }
}
