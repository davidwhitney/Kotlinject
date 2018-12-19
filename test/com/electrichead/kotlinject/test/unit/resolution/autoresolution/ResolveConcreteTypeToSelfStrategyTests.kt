package com.electrichead.kotlinject.test.unit.resolution.autoresolution

import com.electrichead.kotlinject.test.unit.stubs.*
import com.electrichead.kotlinject.resolution.autoresolution.ResolveConcreteTypeToSelfStrategy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ResolveConcreteTypeToSelfStrategyTests {
    private lateinit var _resolver: ResolveConcreteTypeToSelfStrategy

    @BeforeEach
    fun setUp() {
        _resolver = ResolveConcreteTypeToSelfStrategy()
    }

    @Test
    fun discover_concreteClass_returnsSelf() {
        val target = _resolver.discover(Foo::class)!!

        assertEquals(Foo::class, target)
    }

    @Test
    fun discover_interfaceSupplied_returnsNull() {
        val target = _resolver.discover(IFoo::class)

        assertNull(target)
    }
}