package com.electrichead.kotlinject.Test.Unit.javastubs;

public class ConditionalBindingParent2{
    private IConditionalBindingStub dep;

    ConditionalBindingParent2(IConditionalBindingStub dep){

        this.dep = dep;
    }

    public IConditionalBindingStub getDep() {
        return dep;
    }
}