package com.electrichead.kotlinject.test.unit.resolution.autoresolution

import com.electrichead.kotlinject.test.unit.stubs.*
import com.electrichead.kotlinject.test.unit.stubs.BarBar
import com.electrichead.kotlinject.resolution.autoresolution.ResolveFooToFooImplStrategy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MatchFooToFooImplStrategyTests {

    private lateinit var _resolver: ResolveFooToFooImplStrategy

    @BeforeEach
    fun setUp() {
        _resolver = ResolveFooToFooImplStrategy()
    }

    @Test
    fun discover_passedTypeWithMatchingImpl_ReturnsMatchingImpl() {
        val expectedTarget = _resolver.discover(Blah::class)!!

        assertEquals(BlahImpl::class, expectedTarget)
    }

    @Test
    fun discover_passedTypeNoMatchingConcreteImplFound_ReturnsNull() {
        val target = _resolver.discover(BarBar::class)

        assertNull(target)
    }

    @Test
    fun discover_MatchingNameFoundButDoesntImplementInterface_ReturnsNull() {
        val target = _resolver.discover(Blah2::class)

        assertNull(target)
    }
}