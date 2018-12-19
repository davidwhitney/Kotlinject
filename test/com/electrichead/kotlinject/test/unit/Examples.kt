package com.electrichead.kotlinject.test.unit

import com.electrichead.kotlinject.Container
import com.electrichead.kotlinject.test.unit.stubs.*
import com.electrichead.kotlinject.registration.Lifecycle
import com.electrichead.kotlinject.test.unit.stubs.Bar
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Examples {

    @Test
    fun `Auto-binding to default interfaces`() {
        val container = Container()

        val foo = container.resolve<Foo>()
        val foo2 = container.resolve<IFoo>()

        assertNotNull(foo)
        assertNotNull(foo2)
    }

    @Test
    fun `Explicit bindings`() {
        val container = Container()
        container.registrations
                    .bind(Foo::class)
                    .bind(Bar::class)

        val foo = container.resolve<Foo>()
        val foo2 = container.resolve<Bar>()

        assertNotNull(foo)
        assertNotNull(foo2)
    }

    @Test
    fun `Generic bindings`() {
        val container = Container()
        container.registrations
                    .bind<IFoo, Foo>()
                    .bind<Bar, Bar>()

        val foo = container.resolve<Foo>()
        val foo2 = container.resolve<Bar>()

        assertNotNull(foo)
        assertNotNull(foo2)
    }

    @Test
    fun `Bindings to factory functions`() {
        val container = Container()
        container.registrations
                    .bind<IFoo>(createFoo())
                    .bind<IBar>({ Bar() })

        val foo = container.resolve<Foo>()
        val foo2 = container.resolve<Bar>()

        assertNotNull(foo)
        assertNotNull(foo2)
    }

    private fun createFoo() = { Foo() }

    @Test
    fun `Scan for auto-registration`() {
        val container = Container()
        container.registrations.scan
            .fromPackageContaining<IFoo> { x -> x.bindAllInterfaces()  }
            .fromPackageContaining<IFoo> { x -> x.bindClassesToSelf()  }

        val bar = container.resolve<Bar>()

        assertNotNull(bar)
    }

    @Test
    fun `Contextual bindings`() {
        val container = Container()

        container.registrations
            .bindSelf<ConditionalBindingParent1>()
            .bindSelf<ConditionalBindingParent2>()
            .bind<IConditionalBindingStub, ConditionalBindingImplementation1>(condition = {
                    x->x.whenInjectedInto(ConditionalBindingParent1::class)
            })
            .bind<IConditionalBindingStub, ConditionalBindingImplementation2>(condition = {
                    x->x.whenInjectedInto(ConditionalBindingParent2::class)
            })

        val instance1 =  container.resolve<ConditionalBindingParent1>()
        val instance2 =  container.resolve<ConditionalBindingParent2>()

        assertEquals(ConditionalBindingImplementation1::class, instance1.injected::class)
        assertEquals(ConditionalBindingImplementation2::class, instance2.injected::class)
    }

    @Test
    fun `Freeform contextual bindings using onlyWhen`(){
        val container = Container()

        container.registrations
            .bindSelf<ConditionalBindingParent1>()
            .bindSelf<ConditionalBindingParent2>()
            .bind<IConditionalBindingStub, ConditionalBindingImplementation1>()
            .bind<IConditionalBindingStub, ConditionalBindingImplementation2>(condition = {
                    x -> x.onlyWhen { ctx -> ctx.rootType == ConditionalBindingParent2::class }
            })

        val instance1 =  container.resolve<ConditionalBindingParent1>()
        val instance2 =  container.resolve<ConditionalBindingParent2>()

        assertEquals(ConditionalBindingImplementation1::class, instance1.injected::class)
        assertEquals(ConditionalBindingImplementation2::class, instance2.injected::class)
    }


    @Test
    fun `Configure a singleton`(){
        val container = Container()
        container.registrations.bind(IBar::class, Bar::class, lifecycle = Lifecycle.Singleton)

        val instance1 = container.resolve(IBar::class)
        val instance2 = container.resolve(IBar::class)

        assertEquals(instance1, instance2)
    }

    @Test
    fun `Recommended approach`(){
        val container = Container()

        // Scan for everything
        container.registrations.scan
            .fromPackageContaining<IFoo> { x -> x.bindAllInterfaces()  }
            .fromPackageContaining<IFoo> { x -> x.bindClassesToSelf()  }

        // Register factories for special cases
        container.registrations
            .bind<IFoo>(createFoo(), lifecycle = Lifecycle.Singleton)
            .bind<IBar>({ Bar() })

        // Override bindings when you need to switch out implementations
        container.registrations
            .bind<IConditionalBindingStub, ConditionalBindingImplementation1>(condition = {
                    x->x.whenInjectedInto(ConditionalBindingParent1::class)
            })
            .bind<IConditionalBindingStub, ConditionalBindingImplementation2>(condition = {
                    x->x.whenInjectedInto(ConditionalBindingParent2::class)
            })

        val bar = container.resolve<Bar>()

        assertNotNull(bar)
    }
}