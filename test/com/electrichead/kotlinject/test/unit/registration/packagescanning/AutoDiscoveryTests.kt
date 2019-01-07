package com.electrichead.kotlinject.test.unit.registration.packagescanning

import com.electrichead.kotlinject.Container
import com.electrichead.kotlinject.test.unit.stubs.*
import com.electrichead.kotlinject.registration.TypeRegistry
import com.electrichead.kotlinject.registration.packagescanning.AutoDiscovery
import com.electrichead.kotlinject.test.unit.stubs.IFoo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AutoDiscoveryTests {

    private lateinit var _registry: TypeRegistry
    private lateinit var _discovery: AutoDiscovery

    @BeforeEach
    fun setUp() {
        _registry = TypeRegistry()
        _registry.autoDiscovery = false
        _discovery = AutoDiscovery(_registry)
    }

    @Test
    fun scanFromPackageContaining_AutobindsInterfacesFromPackages() {
        _discovery.fromPackageContaining<IFoo> { x -> x.bindAllInterfaces() }

        val somethingElse = _registry.retrieveBindingFor(IBar::class).single()
        assertNotNull(somethingElse.targetType)
    }

    @Test
    fun scanFromPackageContaining_AutobindsClassesFromPackages() {
        _discovery.fromPackageContaining<IFoo> { x -> x.bindClassesToSelf() }

        val somethingElse = _registry.retrieveBindingFor(Bar::class).single()
        assertNotNull(somethingElse.targetType)
    }

    @Test
    fun scanFromClassPath_AutobindsClassesFromPackages() {
        _discovery.fromClasspathWhere(
            { classes -> classes.contains(Bar::class) },
            { c -> c.bindClassesAndInterfaces() })

        val somethingElse = _registry.retrieveBindingFor(Foo::class).single()
        assertNotNull(somethingElse.targetType)
    }

    @Test
    fun autoBinding_SupportsConditions() {
        _registry.bindSelf<TypeWithDependency>()
        _discovery.fromPackageContaining<IFoo> { x ->
            x.bindClassesAndInterfaces (
                { condition -> condition.whenInjectedInto(TypeWithDependency::class) }
            )
        }

        val container = Container(_registry)

        val instanceCreatedFromConditionalBinding = container.resolve<TypeWithDependency>()
        assertEquals(instanceCreatedFromConditionalBinding.foo::class, Foo::class)

        // Cannot create, because bindings including Foo are all conditional.
        assertThrows<Exception> { container.resolve<Foo>() }
    }
}