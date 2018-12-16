Kotlinject
====================

* Introduction
* Installation
* Getting started & Default Behaviours
* Recommendations
* Features
* Contributing
* Credits

---

DI is good. IoC is good.

A good IoC container helps you implement DI in your application, while staying out of your way.

Good DI -

* Uses constructor injection
* Is non-invasive in your codebase
* Supports convention over configuration
* Only configured by exception
* Does not store it's configuration in a myriad of text files
* Doesn't required one big god class that lists every single class and interface in your software
* Is easy to use
* Is easy to debug

This is Kotlinject - named with a hat-tip to Ninject - the most user friendly DI container in .NET.

# Installation

Clone this repository? Profit? (TBC)

# Getting started

There's an Examples.kt file included with the most useful scenarios - but the simplest use-case looks like this

```kotlin

    @Test
    fun `Auto-binding to default interfaces`() {
        val container = Container()

        val foo = container.resolve<Foo>()
        val foo2 = container.resolve<IFoo>()

        assertNotNull(foo)
        assertNotNull(foo2)
    }
```

The container will, by default, when it can't find a matching binding for `IFoo`, attempt to resolve `Foo`.
It'll also always return an implementation for any concrete class you ask for.
This should enable most "just working" scenarios - all instances are transient and unmanaged.

You can disable this fallback discovery by setting the flag

```kotlin
     container.registrations.autoDiscovery = false
```

## Default Behaviour

* Any type you request will be generated
* Circular dependencies will throw an exception
* Any classes configured as singletons will be created only once, and never explicitly destroyed
* You can bind multiple times, and the last binding in will "win"
* The largest constructor will be selected for injection

# Recommendations

* Configure the container to Auto-scan your packages and register defaults.
* Follow a strong IFoo -> Foo naming convention.
* Add explicit bindings for things you have special rules for.
* Have fun!

This would look something like:

```kotlin

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
    }
```

Let's break down all those features...

# Features

Any and all of these features can be mixed and matched.

## Auto-Binding to default implementations


```kotlin

    @Test
    fun `Auto-binding to default interfaces`() {
        val container = Container()

        val foo = container.resolve<Foo>()
        val foo2 = container.resolve<IFoo>()

        assertNotNull(foo)
        assertNotNull(foo2)
    }
```

## Explicit binding syntaxes


```kotlin
    @Test
    fun `Explicit bindings`() {
        val container = Container()
        container.registrations
                    .bind(Foo::class)
                    .bind<Bar, Bar>()

        val foo = container.resolve<Foo>()
        val foo2 = container.resolve<Bar>()

        assertNotNull(foo)
        assertNotNull(foo2)
    }
```

## Delgating construction to factory functions / delegates


```kotlin

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
```

## Package scanning and auto-registration

```kotlin

    @Test
    fun `Scan for auto-registration`() {
        val container = Container()
        container.registrations.scan
            .fromPackageContaining<IFoo> { x -> x.bindAllInterfaces()  }
            .fromPackageContaining<IFoo> { x -> x.bindClassesToSelf()  }

        val bar = container.resolve<Bar>()

        assertNotNull(bar)
    }

```

## Contextual bindings

```kotlin

    @Test
    fun `Contextual bindings`() {
        val container = Container()

        container.registrations
            .bind<ConditionalBindingParent1, ConditionalBindingParent1>()
            .bind<ConditionalBindingParent2, ConditionalBindingParent2>()
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
```

## Lifecycle management

* Singleton

```kotline

    @Test
    fun `Configure a singleton`(){
        val container = Container()
        container.registrations.bind(IBar::class, Bar::class, Lifecycle.Singleton)

        val instance1 = container.resolve(IBar::class)
        val instance2 = container.resolve(IBar::class)
        
        assertEquals(instance1, instance2)
    }
	
```

* Per-Request / Transient

```kotline

    @Test
    fun `Configure a singleton`(){
        val container = Container()
        container.registrations.bind(IBar::class, Bar::class, Lifecycle.PerRequest)

        val instance1 = container.resolve(IBar::class)
        val instance2 = container.resolve(IBar::class)
        
        assertEquals(instance1, instance2)
    }
	
```

# Contributing

Send a pull request with a passing test for any bugs or interesting extension ideas.
I'm only still scratching the surface of Kotlin, so I apologise for any grosse offenses in code.
Feedback gladly appreciated.

# Credits

David Whitney
