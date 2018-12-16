package electrichead.kotlinject.Test.Unit.registration

import electrichead.kotlinject.Test.Unit.stubs.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import electrichead.kotlinject.registration.TypeRegistry
import electrichead.kotlinject.activation.MissingBindingException
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TypeRegistryTests{

    private lateinit var _registry: TypeRegistry

    @BeforeEach
    fun setUp(){
        _registry = TypeRegistry()
    }

    @Test
    fun bindT_AllowsTypesToBeCreated() {
        _registry.autoDiscovery = false

        _registry.bind<IFoo,Foo>()

        val instance = _registry.retrieveBindingFor(IFoo::class)
        assertNotNull(instance.targetType)
    }

    @Test
    fun bind_NoAutoDiscovery_AllowsTypesToBeCreated() {
        _registry.autoDiscovery = false

        _registry.bind(Foo::class, Foo::class)

        val instance = _registry.retrieveBindingFor(Foo::class)
        assertNotNull(instance.targetType)
    }

    @Test
    fun bind_ToSelf_AllowsTypesToBeCreated() {
        _registry.autoDiscovery = false

        _registry.bind(Foo::class)

        val instance = _registry.retrieveBindingFor(Foo::class)
        assertNotNull(instance.targetType)
    }

    @Test
    fun bindTToFactoryFunction_AllowsTypesToBeCreated() {
        _registry.autoDiscovery = false

        _registry.bind<IFoo>({ Foo() })

        val instance = _registry.retrieveBindingFor(IFoo::class)
        assertNotNull(instance.targetDelegate)
    }

    @Test
    fun bind_ToFactoryFunction_AllowsTypesToBeCreated() {
        _registry.autoDiscovery = false

        _registry.bind(Foo::class, { Foo() })

        val instance = _registry.retrieveBindingFor(Foo::class)
        assertNotNull(instance.targetDelegate)
    }

    @Test
    fun retrieveBindingFor_NoBindingsNoDiscovery_Throws() {
        _registry.autoDiscovery = false

        assertThrows<MissingBindingException> {
            _registry.retrieveBindingFor(Foo::class)
        }
    }

    @Test
    fun retrieveBindingFor_NoBindings_CanResolveTypeWithNoDependencies() {
        val instance = _registry.retrieveBindingFor(Foo::class)

        assertNotNull(instance.targetType)
    }

    @Test
    fun retrieveBindingFor_NoBindings_CanResolveTypeWithDependencies(){
        val instance = _registry.retrieveBindingFor(TypeWithDependency::class)

        assertNotNull(instance.targetType)
    }

    @Test
    fun retrieveBindingFor_NoBindings_CanResolveInterfaceWithDefaultImplementation(){
        val instance = _registry.retrieveBindingFor(IFoo::class)

        assertNotNull(instance.targetType)
    }

    @Test
    fun retrieveBindingFor_bindingPresent_CanResolveToBoundImplementation(){
        _registry.bind(IFoo::class, Foo2::class)

        val instance = _registry.retrieveBindingFor(IFoo::class)

        assertEquals("Foo2", instance.targetType!!.simpleName)
    }

    @Test
    fun retrieveBindingFor_chainBindingPresent_CanResolveToBoundImplementation(){
        _registry
            .bind(IFoo::class, Foo2::class)
            .bind(TypeWithDependency::class, TypeWithDependency::class)

        val instance = _registry.retrieveBindingFor(IFoo::class)

        assertEquals("Foo2", instance.targetType!!.simpleName)
    }

    @Test
    fun scanFromPackageContaining_AutobindsInterfacesFromPackages() {
        _registry.autoDiscovery = false

        _registry
            .scan
            .fromPackageContaining<IFoo> { x -> x.bindAllInterfaces()}

        val somethingElse = _registry.retrieveBindingFor(IBar::class)
        assertNotNull(somethingElse.targetType)
    }

    @Test
    fun scanFromPackageContaining_AutobindsClassesFromPackages() {
        _registry.autoDiscovery = false

        _registry
            .scan
            .fromPackageContaining<IFoo> { x -> x.bindClassesToSelf()}

        val somethingElse = _registry.retrieveBindingFor(Bar::class)
        assertNotNull(somethingElse.targetType)
    }
}
