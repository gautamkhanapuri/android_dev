package org.gak.kotlinbyexample

// including parameter names does makes code easier to read. This is called using named arguments

// Function not returning a useful value then its return type is Unit.
//  Unit is a type with only one value – Unit. Unit is returned explicitly in your function body.
typealias OP = (Int, Int) ->Int

fun mainFunction() {
    println("**** Functions ***")

    // minOf as function expression using typealias
    val minOf: OP = {a, b -> if (a < b) a else b}
    // maxOf as function expression
    fun maxOf1(a: Int, b: Int) = if (a > b) a else b

    println("Using kotlin maxOf(0,42) ==> ${kotlin.comparisons.maxOf(0, 42)}")
    println("Using local functions maxOf(0,42) ==> ${maxOf(0, 42)}")
    println("Using Function expression maxOf(0,42) ==> ${maxOf1(0, 42)}")
    println("Using typealias for function expression minOf(0,42)  ==> ${minOf(0, 42)}")
    println("Using typealias for function expression minOf.invoke(0,42)  ==> ${minOf.invoke(0, 42)}")
    // minOf1(1.0, 2.0) - Compilation error
    // minOf(data1, data2) - library function for all numeric data types.
    println("Using typealias for function expression minOf(0,42)  ==> ${minOf(1.0, 2.0)}")

    // function as an expression
    fun sum1(a: Int, b: Int) = a + b
    println("Using Local function sum(3,5) ==>" + sum(3, 5))
    println("Using function expression sum1(3,5) ==>" + sum1(3, 5))
    printSum(3,5)

    printProduct("6", "7")
    printProduct("a", "7")
    printProduct("a", "b")

    printLength("Incomprehensibilities")
    printLength(1000)
    printLength(listOf(Any()))

    foo()
    foo(2, "abcd")
    listExpression()

    // if the last argument after default parameters is a lambda, you can pass it either as a
    //   named argument or outside the parentheses
    //  This is mostly done in compose where last parameter is content
    foo1(1) { println("trailing lambdas called ==> hello") }     // Uses the default value baz = 1
    foo1(qux = { println("Last parameter is function, called as parameter ==> hello") })   // Uses both default values bar = 0 and baz = 1
    foo1 { println("Trailing lambdas called ==> hello") }        // Uses both default values bar = 0 and baz = 1


    printHello("Kotlin")
    printHello(null)

    printHello1("Kotlin 1")
    printHello1(name = null)

    // When the function body consists of a single expression, the curly braces
    //   can be omitted and the body specified after an = symbol
    fun double(x: Int): Int = x * 2

    // Explicitly declaring the return type is optional when this can be
    //   inferred by the compiler:
    fun double1(x: Int) = x * 2
    println("Inline expression without curly brace ==> ${double(1)}")
    println("Inline expression with inferred return type ==> ${double1(2)}")

    val lst = asList1(*arrayOf("a", "b", "c"))
    println("Create a list<String> using vararg function * (AKA spread operator) to disperse arrays as varargs ==> ${lst}")

    // No ternary operator, single line if is used.
    fun max(a: Int, b: Int) = if (a > b) a else b
    println("Single line if function expression max(99, -42) ==> ${max(99, -42)}")

    var neverNull: String = "This can't be null"
    // neverNull = null   // Will cause a compilation error
    var nullable: String? = "You can keep a null here"

    strLength(neverNull)                                    // 8
    // strLength(nullable) - Compilation error
    println(lengthString(null))

    mainVararg()
}

// To safely access properties of an object containing a null value, safe call operator ?. is used.
//  The safe call operator returns null if either the object or one of its accessed properties is null.
//  This is useful to avoid the presence of null values triggering errors in your code.
// Safe calls can be chained so that if any property of an object contains a null value, then null is 
//  returned without an error being thrown. 
//  person.company?.address?.country
// A default value to return if a null value is detected by using the Elvis operator ?:
//  Write on the left-hand side of the Elvis operator what should be checked for a null value.
//  Write on the right-hand side of the Elvis operator what should be returned if a null value is detected.
//  val nullString: String? = null
//  println(nullString?.length ?: 0)
fun lengthString(maybeString: String?): Int? = maybeString?.length

// strLength parameter cannot be null.
fun strLength(notNull: String): Int {                   // 7
    return notNull.length
}

// Pass a variable number of arguments (vararg) with names using the
//  spread operator
//  fun foo(vararg strings: String) { /*...*/ }
//     foo(strings = *arrayOf("a", "b", "c"))
// Mark a parameter of a function (usually the last one) with the
//   vararg modifier
fun <T> asList1(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}

// The Unit return type declaration is also optional. The above code is equivalent to:
// fun printHello(name: String?) { ... }
fun printHello(name: String?): Unit {
    if (name != null)
        println("Name parameter is not null ==> Hello $name")
    else
        println("Null parameter passed ==> Hi there!")
    // `return Unit` or `return` is optional
}

fun printHello1(name: String?) {
    println("Null parameter could be passed ==> Hello $name")
    // `return Unit` or `return` is optional
}

fun foo1(
    bar: Int = 0,
    baz: Int = 1,
    qux: () -> Unit,
) {
    println("bar is ${bar}")
    println("baz is ${baz}")
    qux()
}

fun listExpression() {
    val list = listOf(1, -1, 0)
    val positives = list.filter { x -> x > 0 }
    val positives1 = list.filter { it > 0 }
    println("Filter expressions using x named variable ==>" + positives)
    println("Filter expressions using it variable ==> " + positives1)
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

// Unit return type can be omitted:
fun printSum(a: Int, b: Int): Unit {
    println("Unit return for printSum($a,$b) ==> ${a + b}")
}

fun maxOf(a: Int, b: Int): Int {
    if (a > b) {
        return a
    } else {
        return b
    }
}

// A reference must be explicitly marked as nullable when null value is possible.
// Nullable type names have ? at the end.
fun parseInt(str: String): Int? {
    return str.toIntOrNull()
}

fun printProduct(arg1: String, arg2: String) {
    val x = parseInt(arg1)
    val y = parseInt(arg2)

    // Using `x * y` yields error because they may hold nulls.
    if (x != null && y != null) {
        // x and y are automatically cast to non-nullable after null check
        println("Null check for printProduct($x, $y) ==> ${x * y}")
    }
    else {
        println("Error; '$arg1' or '$arg2' is not a number")
    }
}

fun getStringLength(obj: Any): Int? {
    if (obj !is String) return null

    // `obj` is automatically cast to `String` in this branch
    return obj.length
}

fun printLength(obj: Any) {
    println("length of '$obj ==> ${getStringLength(obj) ?: "Error: The object is not a string"} ")
}

fun foo(a: Int = 0, b: String = "") {
    println("Int ==>$a and string ==> '$b'")
}

fun mainVararg() {
    printAll("Hello", "Hallo", "Salut", "Hola", "你好")                 // 2
    printAllWithPrefix(
        "Hello", "Hallo", "Salut", "Hola", "你好",
        prefix = "Greeting: "                                          // 4
    )
    log("Hello", "Hallo", "Salut", "Hola", "你好")
}

fun printAll(vararg messages: String) {                            // 1
    for (m in messages) print("$m, ")
    println()
}

fun printAllWithPrefix(vararg messages: String, prefix: String) {  // 3
    for (m in messages) print("$prefix$m, ")
    println()
}

fun log(vararg entries: String) {
    printAll(*entries)                                             // 5
}
