package org.gak.kotlinbyexample

// Delegation is a way to make composition as powerful for reuse as
//   inheritance. In delegation, two objects are involved in
//   handling a request: a receiving object delegates operations to its
//   delegate. This is analogous to subclasses deferring requests to
//   parent classes

class Rectangl(val width: Int, val height: Int) {
    fun area() = width * height
}

class Window(val bounds: Rectangl) {
    // Delegation
    fun area() = bounds.area()
}

interface ClosedShape {
    fun area(): Int
}

class Rectangl1(val width: Int, val height: Int) : ClosedShape {
    override fun area() = width * height
}

// The ClosedShape implementation of Window delegates to that of the
//   Rectangle that is bounds
class Window1(private val bounds: Rectangl1) : ClosedShape by bounds

fun mainDelegation1() {
    val wn = Window(Rectangl(2,3))
    println("Delegation Area: ${wn.area()}")

    val wn1 = Window1(Rectangl1(2,3))
    println("Delegration 'by' Area: ${wn1.area()}")
}