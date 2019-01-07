package com.electrichead.kotlinject.test.unit.registration.packagescanning

import com.electrichead.kotlinject.Container
import com.electrichead.kotlinject.registration.Binding
import com.electrichead.kotlinject.registration.Lifecycle
import com.electrichead.kotlinject.test.unit.stubs.*
import com.electrichead.kotlinject.registration.TypeRegistry
import com.electrichead.kotlinject.registration.conditionalbinding.WhenInjectedInto
import com.electrichead.kotlinject.registration.packagescanning.AutoDiscovery
import com.electrichead.kotlinject.test.unit.stubs.IFoo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

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

        val binding = _registry.retrieveBindingFor(Foo::class).single()
        val injectionBinding = binding.condition as WhenInjectedInto

        assertEquals(binding.condition::class, WhenInjectedInto::class)
        assertEquals(injectionBinding.target, TypeWithDependency::class)
    }

    @Test
    fun autoBinding_SupportsLifecycles() {
        _registry.bindSelf<TypeWithDependency>()
        _discovery.fromPackageContaining<IFoo> { x ->
            x.bindClassesAndInterfaces (
                lifecycle = Lifecycle.Singleton
            )
        }

        val binding = _registry.retrieveBindingFor(Foo::class).single()

        assertEquals(binding.lifecycle::class, Lifecycle.Singleton::class)
    }
}