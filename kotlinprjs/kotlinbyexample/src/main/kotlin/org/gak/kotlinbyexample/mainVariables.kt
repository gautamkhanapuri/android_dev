package org.gak.kotlinbyexample

// In Kotlin, a variable is decalred starting with a keyword, val or var,
// followed by the name of the variable.

// val - declares variables that are assigned a value only once.
//       These are immutable, read-only local variables that can’t be
//       reassigned a different value after initialization

// var - declares variables that can be reassigned.
//       These are mutable variables, and their values can be changed
//       after initialization

// type inference - Automatically identifying the data type of a declared variable.
//                  When declaring a variable, the type after the variable name can be omitted

// Variables -  variables can be used only after initializing them.
//              Either initialize a variable at the moment of declaration
//         OR   Declare a variable first (Data type is mandatory) and initialize it later.

// Global variables
val PI = 3.14  // Immutable
var p = 0      // Mutable, type is not required.

fun mainVariables() {
    println("**** Variables ***")
    println("Global Variables; PI ==> $PI and p ==> $p")
    // Declares variable x and initializes it with the value of 5
    val a: Int = 5
    println("Immutable variable a, value ==> $a")

    // Declares variable x and initializes it with the value of 5
    var x: Int = 5
    println("Mutable variable x, value ==> $x")
    // Reassigns a new value of 6 to the variable x
    x += 1
    println("Updated x, value ==> $x")

    // Declares the variable y with the value of 5 with`Int` type is inferred
    val y = 5
    println("Immutable variable y implicit type and initialized, value ==> $y")

    // Declares the variable n without initialization; type is required
    val n: Int
    println("Immutable variable n explicit type required, no Initialization, \"val n: Int\"")
    // Initializes the variable n after declaration
    n = 3
    println("Immutable variable n accessed after initialization, \"n = 3\" ==> $n")

    strTemplates()
}

//val maxOfAB: (a:Int,b:Int)->Int = {
//    if (a>b) {
//        return a
//    } else {
//        return b
//    }
//}

fun strTemplates() {
    println("**** String Templates ***")
    var a = 1
    // simple name in template:
    val s1 = "a ==> $a"
    println(s1)
    a = 2
    // arbitrary expression in template:
    val s2 = "${s1.replace("==>", "was")}, but now ==> $a"
    println(s2)

    fun maxOf(a: Int, b: Int) = if (a > b) a else b
    println("Function expr in template maxOf of 0 and 42 is ${maxOf(0, 42)}")

    val rectangle = Rectangle(5.0, 2.0)
    println("String templates calculation of perimeter ==>   ${rectangle.perimeter}")

    var neverNull: String = "This can't be null"            // 1

    // neverNull = null                                        // 2

    var nullable: String? = "You can keep a null here"      // 3

    nullable = null                                         // 4

    // When inferring types, the compiler assumes non-null for variables
    //   that are initialized with a value.
    var inferredNonNull = "The compiler assumes non-null"   // 5
}

open class Shape

class Rectangle(val height: Double, val length: Double): Shape() {
    val perimeter = (height + length) * 2
}