package electrichead.kotlinject.Test.Unit.registration.packagescanning

import electrichead.kotlinject.Test.Unit.stubs.Bar
import electrichead.kotlinject.Test.Unit.stubs.IBar
import electrichead.kotlinject.Test.Unit.stubs.IFoo
import electrichead.kotlinject.registration.TypeRegistry
import electrichead.kotlinject.registration.packagescanning.AutoDiscovery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class AutoDiscoveryTests{

    private lateinit var _registry: TypeRegistry
    private lateinit var _discovery: AutoDiscovery

    @BeforeEach
    fun setUp(){
        _registry = TypeRegistry()
        _registry.autoDiscovery = false
        _discovery = AutoDiscovery(_registry)
    }

    @Test
    fun scanFromPackageContaining_AutobindsInterfacesFromPackages() {
        _discovery.fromPackageContaining<IFoo> { x -> x.bindAllInterfaces()}

        val somethingElse = _registry.retrieveBindingFor(IBar::class).single()
        assertNotNull(somethingElse.targetType)
    }

    @Test
    fun scanFromPackageContaining_AutobindsClassesFromPackages() {
        _discovery.fromPackageContaining<IFoo> { x -> x.bindClassesToSelf()}

        val somethingElse = _registry.retrieveBindingFor(Bar::class).single()
        assertNotNull(somethingElse.targetType)
}
}