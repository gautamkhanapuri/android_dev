package collections
fun mainExtensionFunctions() {
    val name = "ChatGPT"
    println(name.firstChar())   // Output: C

    val numbers = listOf(1, 2, 3, 4, 5)
    println(numbers.sumEven())  // Output: 6
    MyClass.sayHi()

    val s: String? = null
    println(s.isNullOrEmptyCustom())  // true


}

// In Kotlin, an extension function allows you to add new functions to an existing class without modifying its code.

//Syntax:
//fun ReceiverType.newFunctionName(parametrs): ReturnType {
//    function body
//} TODO: write extension func

// You can pretend as though you are extending an existing class like String, List, Int.
// Under the hood, kotlin does not actually modify the class, it simply compiles it as a static
// utility function.
// myString.collections.firstChar()
// But it actually looks like
// collections.firstChar(myString)              Just syntactic sugar.

// Cheat Sheet
// Extend a class - fun String.newFunc() {}
// Extend nullable - fun String?.newFunc() {}
// Access receiver - this inside function body
// call extension - object.newFunc()

fun String.firstChar(): Char {
    return this[0]
}

fun List<Int>.sumEven(): Int {
    return this.filter { it % 2 == 0 }.sum()
}

class MyClass {
    companion object
}

fun MyClass.Companion.sayHi() {
    println("Hello from companion!")
}

fun String?.isNullOrEmptyCustom(): Boolean {
    return this == null || this.isEmpty()
}

class Dog(val name: String)

fun Dog.bark() {
    println("$name says Woof!")
}