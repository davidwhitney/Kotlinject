package com.electrichead.kotlinject.test.unit

import com.electrichead.kotlinject.CircularDependencyException
import com.electrichead.kotlinject.Container
import com.electrichead.kotlinject.test.unit.stubs.*
import com.electrichead.kotlinject.registration.Lifecycle
import com.electrichead.kotlinject.test.unit.stubs.Bar
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ContainerTests {

    private lateinit var _container: Container

    @BeforeEach
    fun setUp() {
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

    @Test
    fun resolve_SupportsConditionalBindingsBasedOnInjectionTarget(){
        _container.registrations
            .bindSelf<ConditionalBindingParent1>()
            .bindSelf<ConditionalBindingParent2>()
            .bind<IConditionalBindingStub, ConditionalBindingImplementation1>(condition = {
                    x->x.whenInjectedInto(ConditionalBindingParent1::class)
            })
            .bind<IConditionalBindingStub, ConditionalBindingImplementation2>(condition = {
                    x->x.whenInjectedInto(ConditionalBindingParent2::class)
            })

        val instance1 =  _container.resolve<ConditionalBindingParent1>()
        val instance2 =  _container.resolve<ConditionalBindingParent2>()

        assertEquals(ConditionalBindingImplementation1::class, instance1.injected::class)
        assertEquals(ConditionalBindingImplementation2::class, instance2.injected::class)
    }

    @Test
    fun resolve_supportsOnlyWhenBindings(){
        _container.registrations
            .bindSelf<ConditionalBindingParent1>()
            .bindSelf<ConditionalBindingParent2>()
            .bind<IConditionalBindingStub, ConditionalBindingImplementation1>()
            .bind<IConditionalBindingStub, ConditionalBindingImplementation2>(condition = {
                    x -> x.onlyWhen { ctx -> ctx.rootType == ConditionalBindingParent2::class }
            })

        val instance1 =  _container.resolve<ConditionalBindingParent1>()
        val instance2 =  _container.resolve<ConditionalBindingParent2>()

        assertEquals(ConditionalBindingImplementation1::class, instance1.injected::class)
        assertEquals(ConditionalBindingImplementation2::class, instance2.injected::class)
    }

    @Test
    fun resolve_MultipleBindingsExistThatAllMatch_LastOneInWins(){
        _container.registrations
            .bind<IConditionalBindingStub, ConditionalBindingImplementation1>()
            .bind<IConditionalBindingStub, ConditionalBindingImplementation2>()

        val instance =  _container.resolve<IConditionalBindingStub>()

        assertEquals(ConditionalBindingImplementation2::class, instance::class)
    }
}

