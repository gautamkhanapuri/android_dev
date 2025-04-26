package org.gak.kotlinbyexample

// The last parameter of 'calculate' function is a function,
//  so can be invoked with trailing  lambda syntax.
fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {  // 1
    return operation(x, y)                                          // 2
}

fun add(x: Int, y: Int) = x + y // 3

fun subtract(x: Int, y: Int): Int {return x-y}

val divide: (x: Int, y: Int) -> Int = {x: Int, y: Int ->  x/y } // 1


fun mainHigherOrderFunctions() {
    println("**** Higher Order Functions ***")
    val addResult = calculate(4, 5, ::add) // 4

    // Trailing Lambda expression function calling.
    // According to Kotlin convention, if the last parameter of a
    //  function is a function, then a lambda expression passed as
    //  the corresponding argument can be placed outside the parentheses
    //  If the lambda is the only argument in that call, the parentheses
    //   can be omitted entirely
    val mulResult = calculate(4, 5) { a, b -> a * b }
    val subResult = calculate(5, 4, ::subtract) // 5
    val divResult = calculate(5,4, divide)
    println("sumResult $addResult, subResult $subResult mulResult $mulResult divResult $divResult")
}