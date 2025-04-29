package org.gak.kotlinbyexample

// Kotlin's ability to infer the type is called type inference
// In Kotlin, a variable is decalred starting with a keyword, val or var,
// followed by the name of the variable.
// Declare all variables as read-only (val) by default.
// Declare mutable variables (var) only if necessary

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
// Kotlin has the following basic types:
//  Integers          ---> Byte, Short, Int, Long
//  Unsigned Integers ---> UByte, UShort, UInt, ULong
//  Floating Numbers  ---> Float, Double
//  Booleans          ---> Boolean(true or false)
//  Characters        ---> Char
//  Strings           ---> String

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


// String Templates help to print the contents of variables to standard output.
// Template expressions are used to access data stored in variables and other objects,
//  and convert them into strings. A string value is a sequence of characters in double quotes ".
// Template expressions always start with a dollar sign $. To evaluate a piece of code 
//  in a template expression, place the code within curly braces {} after the dollar sign $.

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
