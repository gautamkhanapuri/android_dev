package org.gak.kotlinbyexample

fun mainScopeFunctions() {
    println("**** ScopeFunctions ***")
    mainLet()
    mainRun()
    mainWith()
    mainApply()
    mainAlso()
}

fun customPrint(s: String) {
    print(s.uppercase())
}

// let
//   Usage:   used for scoping and null-checks.
//    Input:  it or a custom name
//    Output: result of lambda expression or last expression

fun printNonNull(str: String?) {
    println("Printing \"$str\":")

    str?.let {
        print("\t")
        customPrint(it)
        println()
    }
}

fun mainLet() {
    println("**** let ***")
    val empty = "test".let { value ->            // 1
        customPrint(value)                    // 2
        value.isEmpty()                       // 3
    }
    println(" is empty: $empty")

    printNonNull(null)
    printNonNull("my string")

}

// run
//   Usage:   useful to call the object's methods rather than pass it as an argument.
//    Input:  this
//    Output: result of lambda expression or last expression
fun getNullableLength(ns: String?) {
    println("for \"$ns\":")
    ns?.run {
        println("\tis empty? " + isEmpty())
        println("\tlength = ${this.length}")
        print("\tcapitalize = ")
        customPrint(this)
        println()
        length   // this.length
    }
}

fun mainRun() {
    println("**** run ***")
    getNullableLength(null)
    getNullableLength("")
    getNullableLength("some string with Kotlin")
}

// with
//   Usage:   access members of its argument viz. omit the instance name when referring to its members
//    Input:  this or the variable name
//    Output: result of lambda expression or last expression
class Student {
    var name: String = ""
    var registered: Boolean = false
}

fun mainWith() {
    println("**** with ***")
    val st = Student()
    val n = with(st) {
        this.name = "Alok"
        registered = true  // this is optional
        st.name  // st is accessible inside the lambda
    }
    println("Student initialized \"with\" ==> $n")
}

// apply
//   Usage:   executes a block of code on an object and returns the object itself.
//    Input:  this
//    Output: the "this" object is returned.

data class Diver(var name: String, var age: Int, var about: String) {
    constructor() : this("", 0, "")
}

fun mainApply() {
    println("**** apply ***")
    val jake = Diver().apply {
        this.name = "Jake"  // this is available
        age = 30
        about = "Deep Sea 200 feet."
    }
    println("Diver Jake using \"apply\" ==> ${jake.toString()}")
}

// also
//   Usage:   executes a block of code on an object and returns the object itself.
//    Input:  it
//    Output: the "this" object is returned.
fun writeCreationLog(p: Diver) {
    println("A new diver ${p.name} was created.")
}

fun mainAlso() {
    println("**** also ***")
    val jake = Diver().apply {
        this.name = "Jake"  // this is available
        age = 30
        about = "Deep Sea 200 feet."
    }
        .also {                                          // 2
            writeCreationLog(it)                         // 3
        }
    println("Diver Jake created using \"apply\" and log written using  \"also\" ==> ${jake.toString()}")
}