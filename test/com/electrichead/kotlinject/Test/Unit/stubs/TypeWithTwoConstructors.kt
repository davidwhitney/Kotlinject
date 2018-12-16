package com.electrichead.kotlinject.test.unit.stubs

import com.electrichead.kotlinject.test.unit.stubs.Bar

class TypeWithTwoConstructors {
    private var _foo: Foo
    private lateinit var _bar: Bar

    constructor(foo : Foo){
        _foo = foo
    }

    constructor(foo : Foo, bar: Bar){
        _foo = foo
        _bar = bar
    }
}


