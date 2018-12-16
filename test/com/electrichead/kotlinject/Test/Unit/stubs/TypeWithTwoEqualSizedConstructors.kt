package com.electrichead.kotlinject.test.unit.stubs

import com.electrichead.kotlinject.test.unit.stubs.Bar

class TypeWithTwoEqualSizedConstructors {
    private lateinit var _foo: Foo
    private lateinit var _bar: Bar

    constructor(foo : Foo){
        _foo = foo
    }

    constructor(bar: Bar){
        _bar = bar
    }
}