package electrichead.kotlinject.Test.Unit.registration.conditionalbinding

import electrichead.kotlinject.Test.Unit.stubs.Bar
import electrichead.kotlinject.activation.ActivationContext
import electrichead.kotlinject.registration.conditionalbinding.AlwaysMatches
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

