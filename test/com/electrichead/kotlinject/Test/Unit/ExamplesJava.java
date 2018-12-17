package com.electrichead.kotlinject.Test.Unit;

import com.electrichead.kotlinject.Test.Unit.javastubs.*;
import org.junit.jupiter.api.*;
import com.electrichead.kotlinject.Container;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        container.getRegistrations()
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

        container.getRegistrations()
                .bind(Far.class, () -> new Far())
                .bind(Bas.class, () -> new Bas());

        var foo = container.resolve(Far.class);
        var foo2 = container.resolve(Bas.class);

        assertNotNull(foo);
        assertNotNull(foo2);
    }
}

