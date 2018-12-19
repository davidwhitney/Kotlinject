package com.electrichead.kotlinject.test.unit.registration.conditionalbinding

import com.electrichead.kotlinject.test.unit.stubs.Bar
import com.electrichead.kotlinject.activation.ActivationContext
import com.electrichead.kotlinject.registration.conditionalbinding.AlwaysMatches
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class AlwaysMatchesTests {
    private lateinit var _condition: AlwaysMatches

    @BeforeEach
    fun setUp() {
        _condition = AlwaysMatches()
    }

    @Test
    fun match_ReturnsTrue() {
        val context = ActivationContext(Bar::class)

        val result = _condition.matches(context)

        assertTrue(result)
    }
}

