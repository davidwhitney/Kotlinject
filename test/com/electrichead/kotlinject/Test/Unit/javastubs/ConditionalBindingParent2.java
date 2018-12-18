package com.electrichead.kotlinject.test.unit.javastubs;

public class ConditionalBindingParent2{
    private IConditionalBindingStub dep;

    public ConditionalBindingParent2(IConditionalBindingStub dep){

        this.dep = dep;
    }

    public IConditionalBindingStub getDep() {
        return dep;
    }
}
