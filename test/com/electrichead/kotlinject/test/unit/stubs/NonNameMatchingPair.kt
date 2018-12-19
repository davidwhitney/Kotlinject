package com.electrichead.kotlinject.test.unit.stubs

interface INoneNameMatchingPair
class DifferentName : INoneNameMatchingPair

interface IBadImplementationCheck
class BadImplementationCheck // Doesn't implement interface