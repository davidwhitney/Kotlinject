package electrichead.kotlinject.Test.Unit.registration.conditionalbinding

import electrichead.kotlinject.Test.Unit.stubs.TypeWithDependency
import electrichead.kotlinject.activation.ActivationContext
import electrichead.kotlinject.registration.conditionalbinding.OnlyWhen
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OnlyWhenTests {
    @Test
    fun match_executesProvidedFunction_HonorsResult() {
        val condition = OnlyWhen(filter = { ctx -> true})

        val context = ActivationContext(TypeWithDependency::class)
        val result = condition.matches(context)

        assertTrue(result)
    }

    @Test
    fun match_executesProvidedFunction_HonorsResult2() {
        val condition = OnlyWhen(filter = { ctx -> false})

        val context = ActivationContext(TypeWithDependency::class)
        val result = condition.matches(context)

        assertFalse(result)
    }
}