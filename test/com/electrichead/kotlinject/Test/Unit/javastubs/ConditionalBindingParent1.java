package com.electrichead.kotlinject.Test.Unit.javastubs;

public class ConditionalBindingParent1{
    private IConditionalBindingStub dep;

    ConditionalBindingParent1(IConditionalBindingStub dep){

        this.dep = dep;
    }

    public IConditionalBindingStub getDep() {
        return dep;
    }
}
