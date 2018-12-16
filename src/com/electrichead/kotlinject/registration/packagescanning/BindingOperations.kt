package com.electrichead.kotlinject.registration.packagescanning

class BindingOperations {
    fun bindAllInterfaces() : IBindingStrategy {
        return BindAllInterfaces()
    }

    fun bindClassesToSelf() : IBindingStrategy {
        return BindClassesToSelf()
    }
}