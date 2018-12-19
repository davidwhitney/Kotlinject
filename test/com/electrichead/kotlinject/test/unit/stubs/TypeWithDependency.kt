package com.electrichead.kotlinject.test.unit.stubs

class TypeWithDependency{
    private var _foo: Foo
    constructor(foo : Foo){
        _foo = foo
    }
}


