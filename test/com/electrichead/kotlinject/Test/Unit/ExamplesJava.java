package com.electrichead.kotlinject.Test.Unit;

import com.electrichead.kotlinject.Test.Unit.javastubs.*;
import org.junit.jupiter.api.*;
import com.electrichead.kotlinject.Container;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
public class ExamplesJava {

    @Test
    public void AutobindingToDefaultInterfaces() {
        var container = new Container();

        var foo = container.resolve(Far.class);
        var foo2 = container.resolve(IFar.class);

        assertNotNull(foo);
        assertNotNull(foo2);
    }

    @Test
    public void ExplicitBindings() {
        var container = new Container();
        container.registrations()
                .bind(Far.class, Far.class)
                .bind(Bas.class, Bas.class);

        var foo = container.resolve(Far.class);
        var foo2 = container.resolve(Bas.class);

        assertNotNull(foo);
        assertNotNull(foo2);
    }

    @Test
    public void BindingsToFactoryFunctions(){
        var container = new Container();

        container.registrations()
                .bind(Far.class, () -> new Far())
                .bind(Bas.class, () -> new Bas());

        var foo = container.resolve(Far.class);
        var foo2 = container.resolve(Bas.class);

        assertNotNull(foo);
        assertNotNull(foo2);
    }

    @Test
    public void ScanForAutoRegistrations() {
        var container = new Container();
        container.registrations().scan()
            .fromPackageContaining(Far.class, x -> x.bindAllInterfaces())
            .fromPackageContaining(Far.class, x -> x.bindClassesToSelf());

        var bar = (Far)container.resolve(Far.class);

        assertNotNull(bar);
    }

    @Test
    public void ContextualBindings() {
        var container = new Container();

        container.registrations()
                .bind(ConditionalBindingParent1.class)
                .bindSelf(ConditionalBindingParent2.class)
                .bind(IConditionalBindingStub.class, ConditionalBindingImplementation1.class)
                .bind(IConditionalBindingStub.class, ConditionalBindingImplementation2.class,
                    x -> x.whenInjectedInto(ConditionalBindingParent2.class));

        var instance1 = (ConditionalBindingParent1) container.resolve(ConditionalBindingParent1.class);
        var instance2 =  (ConditionalBindingParent2)container.resolve(ConditionalBindingParent2.class);

        assertEquals(ConditionalBindingImplementation1.class, instance1.getDep().getClass());
        assertEquals(ConditionalBindingImplementation2.class, instance2.getDep().getClass());
    }
}

