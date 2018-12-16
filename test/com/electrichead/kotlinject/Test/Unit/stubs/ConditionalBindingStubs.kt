package com.electrichead.kotlinject.test.unit.stubs

interface IConditionalBindingStub
class ConditionalBindingImplementation1 : IConditionalBindingStub
class ConditionalBindingImplementation2 : IConditionalBindingStub

class ConditionalBindingParent1(dep : IConditionalBindingStub){
    val injected = dep
}

class ConditionalBindingParent2(dep : IConditionalBindingStub){
    val injected = dep
}