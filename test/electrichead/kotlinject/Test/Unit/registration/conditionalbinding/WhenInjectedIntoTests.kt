package electrichead.kotlinject.Test.Unit.registration.conditionalbinding

import electrichead.kotlinject.Test.Unit.stubs.Foo
import electrichead.kotlinject.Test.Unit.stubs.TypeWithDependency
import electrichead.kotlinject.activation.ActivationContext
import electrichead.kotlinject.registration.conditionalbinding.WhenInjectedInto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WhenInjectedIntoTests {
    private lateinit var _condition: WhenInjectedInto

    @BeforeEach
    fun setUp() {
        _condition = WhenInjectedInto(TypeWithDependency::class)
    }

    @Test
    fun match_typeMatchesLastPartOfInjectionHistory_returnsTrue() {
        val context = ActivationContext(TypeWithDependency::class)

        val result = _condition.matches(context)

        assertTrue(result)
    }

    @Test
    fun match_typeDoesntMatchLastPartOfInjectionHistory_returnsFalse() {
        val context = ActivationContext(Foo::class)

        val result = _condition.matches(context)

        assertFalse(result)
    }
}

