package introduction

open class Dog {                // 1
    open fun sayHello() {       // 2
        println("wow wow!")
    }
}

class Yorkshire : Dog() {       // 3
    override fun sayHello() {   // 4
        println("wif wif!")
    }
}

// All Kotlin classes are final by default.
// All Kotlin methods are also final by default.
// open modifier allows modifying them.
// overriding methods require the override modifier not @.

open class Tiger(val origin: String) {
    fun sayHello() {
        println("A tiger from $origin says: hello!")
    }
}

// If you want to use a parameterized constructor of the superclass when creating a subclass,
// provide the constructor arguments in the subclass declaration.
class SiberianTiger : Tiger("Siberian")

open class Lion(val name: String, val age: Int, val origin: String) {
    fun sayHello() {
        println("$name, the lion from $origin says: graoh!")
    }
}

class Asiatic(name:String) : Lion(name = name, age = 25, origin = "Asiatic")
