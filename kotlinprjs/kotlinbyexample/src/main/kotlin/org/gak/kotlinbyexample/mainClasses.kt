package org.gak.kotlinbyexample

fun mainClasses() {
    println("**** Classes ***")
    val user = User1("Alex", 1)
    println("Data class with equals override ==> $user")                                          // 3

    val secondUser = User1("Alex", 1)
    val thirdUser = User1("Max", 2)

    println("Equality operator user == secondUser ==> ${user == secondUser}")   // 4
    println("Equality operator user == thirdUser ==> ${user == thirdUser}")

    val zoo = Zoo(listOf(Animal("zebra"), Animal("lion")))
    for (animal in zoo) {                                   // 3
        println("Iterator for classes ==> Watch out, it's a ${animal.name}")
    }

    validateClasses()
}

data class User1(val name: String, val id: Int) {           // 1
    override fun equals(other: Any?) =
        other is User1 && other.id == this.id               // 2
}


class Animal(val name: String)

class Zoo(val animals: List<Animal>) {

    // define your own iterators in your classes by implementing
    //   the iterator operator in them
    // The iterator can be declared in the type or as an extension function.
    operator fun iterator(): Iterator<Animal> {             // 1
        return animals.iterator()                           // 2
    }
}

// The class declaration consists of the
// class name - Contact
// the class header
//    (  specifying its type parameters,
//       the primary constructor etc.)
//       and the class body, surrounded by curly braces.

class Contact constructor(private val id: Int, var email: String) {
    override fun toString(): String {
        return "Contact(id=$id, email=$email"
    }
}
// Both the header and the body are optional;
// if the class has no body, curly braces can be omitted.
class Customer                                  // 1


fun validateClasses() {

    val customer = Customer()                   // 3
    println("No header and body class ==> $customer")

    val contact = Contact(1, "mary@gmail.com") // 4
    println("Class ==>  $contact")
    // println(contact.id)                         // 5
    contact.email = "jane@gmail.com"            // 6
    println("Update Class ==> $contact")

    val dog: Dog = Yorkshire()
    // Derived class instance held in base class variable, will call the instance method.
    dog.sayHello()

    val lion: Lion = Asiatic("Rufo")                              // 2
    lion.sayHello()
}

// Kotlin classes are final by default, mark them "open" to be inherited
open class Dog {                // 1
    // Kotlin methods are also final by default, mark them "open" to be overridden
    open fun sayHello() {       // 2
        println("Base Class ==> wow wow!")
    }
}

// A class inherits a superclass as : SuperclassName() after its name.
// The empty parentheses () indicate an invocation of the superclass default constructor.
class Yorkshire : Dog() {       // 3
    // Overriding methods or attributes requires the override modifier.
    override fun sayHello() {   // 4
        println("Derived Class ==> wif wif!")
    }
}


open class Lion(val name: String, val origin: String) {
    fun sayHello() {
        println("$name, the lion from $origin says: graoh!")
    }
}

class Asiatic(name: String) : Lion(name = name, origin = "India")