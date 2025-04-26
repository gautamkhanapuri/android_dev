package org.gak.kotlinbyexample

// Infix Functions in the Standard Library:
//Kotlin's standard library also includes several infix functions, such as:
// to: Creates a Pair from two values.
// downTo: Creates a descending sequence of numbers.
// step: Specifies the increment for a sequence.

// Infix Notation:
//  They allow you to call a function without using dots and parentheses,
//   as in the standard Kotlin syntax. Instead, you place the function name between the receiver object and the parameter.
// Single Parameter:
//  Infix functions must have only one parameter.
// Member or Extension Functions:
//  They can be either member functions (functions inside a class) or extension
//   functions (functions that extend the functionality of a class).

// Infix function calls have lower precedence than arithmetic operators, type casts,
//   and the rangeTo operator. The following expressions are equivalent:
//   1 shl 2 + 3 is equivalent to 1 shl (2 + 3)
//   0 until n * 2 is equivalent to 0 until (n * 2)

// Infix functions always require both the receiver and the parameter to be specified.
//  When you're calling a method on the current receiver using the infix notation, use
//  "this" explicitly. This is required to ensure unambiguous parsing.


fun mainExtensions() {
    println("**** Extensions ***")
    infix fun String.onto(other: String) = Pair(this, other)   // 4
    val myPair = "McLaren" onto "Lucas"
    println("onto extension has been added on String to generate a Pair ==> $myPair")

    val sophia = Person("Sophia")
    val claudia = Person("Claudia")
    sophia likes claudia
    println("Sophia likes claudia ==> + ${sophia.toString()}")

    infix fun Int.repeats(str: String) = str.repeat(this)        // 1
    println("Repeat Until for a String  '20 repeats \"*\"' ==> ${20 repeats  "*"}")

    infix fun String.times(v: Int) = this.repeat(v)        // 1
    println("Inline 'times' on String '\"Bye, \" times 2' ==> ${"Bye, " times 2}")

    // The operator symbol for times() is * so that you can call the function using 2 * "Bye".
    operator fun Int.times(str: String) = str.repeat(this)       // 1
    println("Operator extension for Int with String '2.times(\"Bye, \")' ==> ${2.times("Bye, ")}")                                     // 2
    println(" Operator extension as operator symbol for times() is '*' so '2 * \"Bye, \"' ==> ${2 * "Bye, "}")

    // The get() operator enables bracket-access syntax
    operator fun String.get(range: IntRange) = substring(range)  // 3
    val str = "Always forgive your enemies; nothing annoys them so much."
    println("Range operator function for String str[0..14] ==> ${str[0..14]}")

    fun Order.maxPricedItemValue(): Float = this.items.maxByOrNull { it.price }?.price ?: 0F    // 2
    fun Order.maxPricedItemName() = this.items.maxByOrNull { it.price }?.name ?: "NO_PRODUCTS"

    val order = Order(listOf(
        Item("Bread", 25.0F),
        Item("Wine", 29.0F),
        Item("Water", 12.0F)
    ))

    println("Max priced item name: ${order.maxPricedItemName()}")                           // 4
    println("Max priced item value: ${order.maxPricedItemValue()}")
    println("Items: ${order.commaDelimitedItemNames}")
}


class Person(val name: String) {
    val likedPeople = mutableListOf<Person>()
    infix fun likes(other: Person) { likedPeople.add(other) }  // 6
    override fun toString(): String {
        return "Person(name=$name), LikedPeople(${likedPeople.joinToString(",")})"
    }
}

data class Item(val name: String, val price: Float)                                         // 1

data class Order(val items: Collection<Item>)

val Order.commaDelimitedItemNames: String
    get() = items.map { it.name }.joinToString()

// fun Order.maxPricedItemValue(): Float = this.items.maxByOrNull { it.price }?.price ?: 0F    // 2
// fun Order.maxPricedItemName() = this.items.maxByOrNull { it.price }?.name ?: "NO_PRODUCTS"
