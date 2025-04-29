package introduction;

import kotlin.math.pow

// infix functions
// words that act as functions are called infix functions. They are called without dots or
// parenthesis.
// example to, until
// must have exactly one parameter.
// can only be defined on a member method or a extension method.

// general format:
// infix fun ReceiverType.functionName(parameter: ParameterType): ReturnType {
//    ...
//}
// a functionName b ==> a.functionName(b)


fun mainFunctions() {
    println(2 times "Bye ")
    println(5 doubleUp 3)
    println("name" surroundWith "**")
    println(listOf(1, 2, 3) sumWith listOf(4, 5, 6))
    printAll("Hello", "Hallo", "Salut", "Hola", "你好")                 // 2

}

infix fun Int.times(str:String): String {
    return str.repeat(this)
}
// Since the return is a single simple line, we can remove the {} and return and replace it with =
infix fun Int.times1(str: String) = str.repeat(this)

infix fun Int.doubleUp(other: Int): Int {
    return 2 * this * other
}

infix fun String.surroundWith(s: String): String = "$s$this$s"

infix fun List<Int>.sumWith(other: List<Int>): List<Int> {
    val zippedUp = this.zip(other)
    return zippedUp.map { (a, b) -> a + b }
}

infix fun Int.powerOf(other: Int): Double {
    return this.toDouble().pow(other.toDouble())
}

// operator functions
// similar to methods such as __add__ in python.
// +a a.unaryPlus()
data class Point(val x: Int, val y: Int)

operator fun Point.unaryMinus() = Point(-x, -y)

val point = Point(10, 20)

fun testOperatorFunc() {
    println(-point)  // prints "Point(x=-10, y=-20)"
}

// there are many more for in, [], (), +=, ==,

fun printAll(vararg messages: String) {                            // 1
    for (m in messages) println(m)
}

// At runtime, a vararg is just an array. To pass it along into a vararg parameter, use the special spread operator * that lets you pass in *entries (a vararg of String) instead of entries (an Array<String>).
// TODO: how to use *
fun log(vararg entries: String) {
    printAll(*entries)                                             // 5
}