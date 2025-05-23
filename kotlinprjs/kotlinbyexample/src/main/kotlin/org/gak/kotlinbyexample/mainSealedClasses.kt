package org.gak.kotlinbyexample

// Sealed classes restrict the use of inheritance
// They can be subclassed from inside the same package where the sealed class is declared.
// It cannot be subclassed outside of the package where the sealed class is declared.
sealed class Mammal(val name: String)                                                   // 1

class Cat(val catName: String) : Mammal(catName)                                        // 2
class Human(val humanName: String, val job: String) : Mammal(humanName)

fun greetMammal(mammal: Mammal): String {
    when (mammal) {                                                                     // 3
        is Human -> return "Hello ${mammal.name}; You're working as a ${mammal.job}"    // 4
        is Cat -> return "Hello ${mammal.name}"                                         // 5
    }                                                                                   // 6
}

fun mainSealedClasses() {
    println("**** Sealed Classes ***")
    println(greetMammal(Cat("Snowy")))
}