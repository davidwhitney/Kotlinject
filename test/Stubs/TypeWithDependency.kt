package test.Stubs

class TypeWithDependency{
    private var _foo: Foo
    constructor(foo : Foo){
        _foo = foo
    }
}