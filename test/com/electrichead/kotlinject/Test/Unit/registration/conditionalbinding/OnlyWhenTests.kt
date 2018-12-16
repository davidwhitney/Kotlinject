package com.electrichead.kotlinject.test.unit.registration.conditionalbinding

import com.electrichead.kotlinject.test.unit.stubs.*
import com.electrichead.kotlinject.activation.ActivationContext
import com.electrichead.kotlinject.registration.conditionalbinding.OnlyWhen
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OnlyWhenTests {
    @Test
    fun match_executesProvidedFunction_HonorsResult() {
        val condition = OnlyWhen(filter = { true })

        val context = ActivationContext(TypeWithDependency::class)
        val result = condition.matches(context)

        assertTrue(result)
    }

    @Test
    fun match_executesProvidedFunction_HonorsResult2() {
        val condition = OnlyWhen(filter = { false })

        val context = ActivationContext(TypeWithDependency::class)
        val result = condition.matches(context)

        assertFalse(result)
    }
}