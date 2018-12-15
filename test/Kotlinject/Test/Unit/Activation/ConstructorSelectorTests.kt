package Kotlinject.Test.Unit.Activation

import Kotlinject.Activation.ConstructorSelector
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import Kotlinject.Test.Unit.Stubs.Bar
import Kotlinject.Test.Unit.Stubs.TypeWithTwoConstructors
import Kotlinject.Test.Unit.Stubs.TypeWithTwoEqualSizedConstructors
import kotlin.test.assertEquals

class ConstructorSelectorTests {

    private lateinit var _selector: ConstructorSelector

    @BeforeEach
    fun setUp(){
        _selector = ConstructorSelector()
    }

    @Test
    fun typeWithNoConstructors_DefaultCtorSelected() {
        var ctor = _selector.select(Bar::class)

        assertEquals(0, ctor.parameters.count())
    }

    @Test
    fun typeWithTwoConstructors_LargestCtorSelected() {
        var ctor = _selector.select(TypeWithTwoConstructors::class)

        assertEquals(2, ctor.parameters.count())
    }

    @Test
    fun typeWithTwoEqualSizedConstructors_FirstCtorSelected() {
        var ctor = _selector.select(TypeWithTwoEqualSizedConstructors::class)

        assertEquals("foo", ctor.parameters[0].name)
    }
}
