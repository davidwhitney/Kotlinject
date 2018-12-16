package electrichead.kotlinject.Test.Unit

import electrichead.kotlinject.Container
import electrichead.kotlinject.Test.Unit.stubs.Bar
import electrichead.kotlinject.Test.Unit.stubs.Foo
import electrichead.kotlinject.Test.Unit.stubs.IBar
import electrichead.kotlinject.Test.Unit.stubs.IFoo
import org.junit.jupiter.api.Test
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
}