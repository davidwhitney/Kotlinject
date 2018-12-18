package com.electrichead.kotlinject.test.unit.javastubs;

public class ConditionalBindingParent1{
    private IConditionalBindingStub dep;

    public ConditionalBindingParent1(IConditionalBindingStub dep){

        this.dep = dep;
    }

    public IConditionalBindingStub getDep() {
        return dep;
    }
}
