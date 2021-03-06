package com.electrichead.kotlinject.test.unit.resolution.autoresolution

import com.electrichead.kotlinject.test.unit.stubs.*
import com.electrichead.kotlinject.resolution.autoresolution.ResolveIFooToFooStrategy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MatchFooToIFooStrategyTests {

    private lateinit var _resolver: ResolveIFooToFooStrategy

    @BeforeEach
    fun setUp() {
        _resolver = ResolveIFooToFooStrategy()
    }

    @Test
    fun discover_passedIPrefixedType_ReturnsNonIPrefixAtSameLocation() {
        val target = _resolver.discover(IFoo::class)!!

        assertEquals(Foo::class, target)
    }

    @Test
    fun discover_passedIPrefixedTypeNoMatchingConcreteFound_ReturnsNull() {
        val target = _resolver.discover(INoneNameMatchingPair::class)

        assertNull(target)
    }

    @Test
    fun discover_MatchingNameFoundButDoesntImplementInterface_ReturnsNull() {
        val target = _resolver.discover(IBadImplementationCheck::class)

        assertNull(target)
    }
}

