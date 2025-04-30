package scopefunctions

import kotlin.random.Random

//The Kotlin standard library contains several functions whose sole purpose is to execute a block of
//code within the context of an object.
// When you call such a function on an object with a lambda expression provided, it forms a temporary scope.
// In this scope, you can access the object without its name.
// Such functions are called scope functions.
// There are five of them:
// let
// run
// with
// apply
// also

// These functions all perform the same action: execute a block of code on an object.
// What's different is how this object becomes available inside the block and what the result of the whole expression is.
class MultiportService(var uri_In: String, var port: Int) {

    fun query(q: String): Unit {

    }

    fun prepareRequest(): String {
        return uri_In
    }

}
fun mainScopeFunctions() {
    takeIfExample1("010000011", "11")
    takeIfExample1("010000011", "12")
}

class Person(val name: String) {
    var age: Int
        get() {
            TODO()
        }
        set(value) {}
    lateinit var location: String

}

fun letExample() {
    // The context is available as an argument (it).
    // The return value is the lambda result.
    // Let can be used to invoke one or more functions on results of call chains.
    val numbers = mutableListOf("one", "two", "three")
    numbers.map { it.length }.filter { it > 3 }.let { println(it) }

    // If the code block passed to let contains a single function with it as an argument, you can
    // use the method reference (::) instead of the lambda argument.

    numbers.map { it.length }.filter { it > 3 }.let(::println)

    val numbers1 = listOf("one", "two", "three", "four")
    val modifiedFirstItem = numbers1.first().let { firstItem ->
        println("The first item of the list is '$firstItem'")
        if (firstItem.length >= 5) firstItem else "!$firstItem!"
    }.uppercase()
    println("First item after modifications: '$modifiedFirstItem'")
}

fun withExample() {
    // The context object is available as a receiver (this)
    // The return value is the lambda result.
    val numbers1 = mutableListOf("one", "two", "three")
    with(numbers1) { // With can be called as a function. With is not called using the dot notation on the context object.
        println("'with' is called with argument $this")
        println("It contains $size elements")  // There is no need to specify this.size
    }

    // You can also use with to introduce a helper object whose properties or functions are used for
    // calculating a value.
    val numbers2 = mutableListOf("one", "two", "three")
    val firstAndLast = with(numbers2) {
        "The first element is ${first()}," +
                " the last element is ${last()}"
    }
    println(firstAndLast)
}

fun runExample() {
    // The context object is available as a receiver (this)
    // The return value is the lambda result.
    // 'run' does the same as with but it is implemented as ana extension function. So like 'let', you can
    // call it on the context object using dot notation.
    // ** Run is useful when your lambda both initializes as well as computes the return value.**
    val service = MultiportService("https://example.kotlinlang.org", 80)

    val result = service.run {
        port = 8080
        query(prepareRequest() + " to port $port")
    }

// the same code written with let() function:
    val letResult = service.let {
        it.port = 8080
        it.query(it.prepareRequest() + " to port ${it.port}")
    }


    // You can also invoke run as a non-extension function. The non-extension variant of run has no
    // context object. It returns the lambda object.
    // In code, non-extension run can be read as "run the code block and compute the result."

    val hexNumberRegex = run {
        val digits = "0-9"
        val hexDigits = "A-Fa-f"
        val sign = "+-"

        Regex("[$sign]?[$digits$hexDigits]+")
    }

    for (match in hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ")) {
        println(match.value)
    }
}

fun applyExample() {
    // The context object is available as a receiver (this).
    // The return value is the object itself.
    val adam = Person("Adam").apply {
        age = 20
        location = "New York"
    }
    println(adam)
}

fun alsoExample() {
    // The context object is available as an argument (it).
    // The return value is the object itself.
    // also is useful for performing some actions that tae the context object as an argument.
    // Use also for actions that need refernce to the object rather than its properties and unctions
    // or when you dont want to shadow the this reference from an outer scope.
    // It semantically translates to also do the following with the object.
    val numbers = mutableListOf("one", "two", "three")
    numbers.also(::println).add("four")
}

fun takeIfExample() {
    // like a filtering function
    // takeIf returns this object if it satisfies the given predicate. Otherwise, it returns null. So, takeIf is a filtering function for a single object.
    // takeUnless has the opposite logic of takeIf. When called on an object along with a predicate, takeUnless returns null if it satisfies the given predicate. Otherwise, it returns the object.
    val number = Random.nextInt(100)

    val evenOrNull = number.takeIf { it % 2 == 0 }
    val oddOrNull = number.takeUnless { it % 2 == 0 }
    println("even: $evenOrNull, odd: $oddOrNull")
}

fun takeIfExample1(input: String = "abcdefgh", sub: String = "def") {
    input.indexOf(sub).takeIf { it >= 0 }?.let {
        println("The substring $sub is found in $input.")
        println("Its start position is $it.")
    }

    // same as
//    val index = input.indexOf(sub)
//    if (index >= 0) {
//        println("The substring $sub is found in $input.")
//        println("Its start position is $index.")
//    }
}